package org.burlingame.ftc.lib.commands;

import org.burlingame.ftc.lib.subsystem.Subsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

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

    private LinkedList<Command> newCommands = new LinkedList<>();
    private SentinelNode sentinel = new SentinelNode();

    private ArrayList<Subsystem> subsystems = new ArrayList<>(10);
    public void registerSubsystem(Subsystem subsystem) {
        subsystems.add(subsystem);
    }

    /**
     * Schedule a command for execution. It will not be executed immediately. Commands added later
     * will take precedent over earlier commands.
     *
     * @param cmd the command to add
     */
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

    private void addCommandsFromBuffer() {
        addingFromBuffer = true;

        Set<Subsystem> reserved = new HashSet<>();

        for (Iterator<Command> it = newCommands.descendingIterator(); it.hasNext(); ) {
            Command cmd = it.next();
            boolean shouldAdd = true;
            for (Subsystem sub : cmd.requiredSubsystems) {
                if (reserved.contains(sub)) {
                    shouldAdd = false;
                    break;
                }
            }
            if (shouldAdd) {
                reserved.addAll(cmd.requiredSubsystems);
                _add(cmd);
            }
        }

        newCommands.clear();
        addingFromBuffer = false;
    }

    public void loop() {
        addCommandsFromBuffer();

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
