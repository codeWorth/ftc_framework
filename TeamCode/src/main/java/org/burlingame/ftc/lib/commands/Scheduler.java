package org.burlingame.ftc.lib.commands;

import org.burlingame.ftc.lib.subsystem.Subsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

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
            addDefaultCommand(sub);
        }
    }

    private void addDefaultCommand(Subsystem sub) {
        Command def = sub.getDefaultCommand();
        if (def != null) {
            add(def);
        }
    }

    private void addCommandsFromBuffer() {
        addingFromBuffer = true;

        Map<Subsystem, Command> reserved = new HashMap<>();
        LinkedList<Command> actuallyAdded = new LinkedList<>();

        // Go in reverse order, since newer commands take precedent.
        for (Iterator<Command> it = newCommands.descendingIterator(); it.hasNext(); ) {
            Command cmd = it.next();
            boolean shouldAdd = true;
            for (Subsystem sub : cmd.requiredSubsystems) {
                if (reserved.containsKey(sub)) {
                    shouldAdd = false;
                    break;
                }
            }
            if (shouldAdd) {
                for (Subsystem sub: cmd.requiredSubsystems) {
                    reserved.put(sub, cmd);
                }
                actuallyAdded.addFirst(cmd);  // Add the reversed commands in reversed order
            }
        }

        for (Subsystem sub : reserved.keySet()) {
            Command cmd = reserved.get(sub);
            Command existing = sub.currentCommand;
            if (existing != null) {
                existing._interrupted();
                for (Subsystem existingSub : existing.requiredSubsystems) {
                    existingSub.currentCommand = null;
                }
                existing.remove();
            }
            sub.currentCommand = cmd;
        }

        for (Command cmd: actuallyAdded) {
            _add(cmd);
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
                    sub.currentCommand = null;
                    addDefaultCommand(sub);
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

        @Override
        public String toString() {
            if (this.next == this) {
                return "[]";
            }
            if (this.next.next == this) {
                return "[" + this.next + "]";
            }
            StringBuilder sb = new StringBuilder("[");
            Command cmd = this.next;
            sb.append(cmd);
            for (cmd = cmd.next; cmd.next != sentinel; cmd = cmd.next) {
                sb.append(", ");
                sb.append(cmd);
            }
            sb.append("]");
            return sb.toString();
        }
    }

}
