package org.burlingame.ftc.lib.commands;

import org.burlingame.ftc.lib.subsystem.Subsystem;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ParallelGroup extends Command {

    private LinkedList<Command> children = new LinkedList<>();
    private LinkedList<Command> running = new LinkedList<>();

    ParallelGroup(Command first, Command second) {
        this.and(first).and(second);
    }

    @Override
    public ParallelGroup and(Command other) {
        children.add(other);
        for (Subsystem sub: requiredSubsystems) {
            if (other.requiredSubsystems.contains(sub)) {
                throw new IllegalArgumentException("Parallel commands should not require the same subsystem!");
            }
        }
        require(other.requiredSubsystems);
        return this;
    }

    @Override
    protected void init() {
        running.addAll(children);
    }

    @Override
    protected void execute() {
        for (ListIterator<Command> it = running.listIterator(); it.hasNext(); ) {
            Command cmd = it.next();
            if (cmd.run()) {
                it.remove();
                cmd._end();
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return running.isEmpty();
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        for (Command cmd: running) {
            cmd._interrupted();
        }
    }

}
