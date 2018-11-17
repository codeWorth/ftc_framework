package org.burlingame.ftc.lib.commands;

import org.burlingame.ftc.lib.subsystem.Subsystem;

import java.util.ArrayList;

public class Scheduler {

    private static Scheduler instance;
    public static Scheduler getInstance() {
        if (instance == null) {
            instance = new Scheduler();
        }

        return instance;
    }

    public boolean inTeleop = false;
    private boolean addingFromBuffer = false;

    private ArrayList<Command> newCommands = new ArrayList<>(30);
    private SentinelNode sentinel = new SentinelNode();

    private ArrayList<Subsystem> subsystems = new ArrayList<>(10);
    public void registerSubsystem(Subsystem subsystem) {
        subsystems.add(subsystem);
    }

    public void add(Command cmd) {
        if (addingFromBuffer) {
            throw new IllegalArgumentException("Can't add a command at this time, either race condition, or someone put Scheduler.getInstance().add(command) in a constructor (put in initialize!)");
        }
        if (cmd != null) {
            newCommands.add(cmd);
        }
    }

    private void _add(Command cmd) {
        Command prev = sentinel.prev;
        Command next = sentinel;
        prev.next = cmd;
        cmd.prev = prev;
        cmd.next = next;
        next.prev = cmd;
    }

    public void init() {
        for (Subsystem sub : subsystems) {
            if (sub.getDefaultCommand() != null) {
                _add(sub.getDefaultCommand());
            }
        }
    }

    public void loop() {
        int size = newCommands.size();
        addingFromBuffer = true;
        for (int i = 0; i < size; i++) {
            Command cmd = newCommands.get(i);
            _add(cmd);
        }
        newCommands.clear();
        addingFromBuffer = false;

        for (Command cmd = sentinel.next; cmd != sentinel; cmd = cmd.next) {
            if (cmd.run()) {
                cmd.remove();
                for (Subsystem sub : cmd.requiredSubsystems) {
                    if (sub.getDefaultCommand() != null) {
                        add(sub.getDefaultCommand());
                    }
                }
                cmd._end();
            }
        }
    }

    public void endAuto() {
        clearCommands();
        inTeleop = true;
    }

    public void endTeleop() {
        clearCommands();
        inTeleop = false;
    }

    /**
     * Remove all commands.
     */
    public void clearCommands() {
        newCommands.clear();
        for (Command cmd = sentinel.next; cmd != sentinel; cmd = cmd.next) {
            cmd._interrupted();
        }
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /**
     * Reset entire scheduler to default state (no commands, no subsystems)
     */
    public void reset() {
        clearCommands();
        subsystems.clear();
    }

    private class SentinelNode extends Command {
        SentinelNode() {
            super();
            next = this;
            prev = this;
        }
        @Override
        protected void execute() {
            throw new IllegalStateException("SentinelNode should not be called!");
        }

        @Override
        public void init() {
            throw new IllegalStateException("SentinelNode should not be called!");
        }

        @Override
        protected boolean isFinished() {
            throw new IllegalStateException("SentinelNode should not be called!");
        }

        @Override
        public void end() {
            throw new IllegalStateException("SentinelNode should not be called!");
        }

        @Override
        public void interrupted() {
            throw new IllegalStateException("SentinelNode should not be called!");
        }
    }

}
