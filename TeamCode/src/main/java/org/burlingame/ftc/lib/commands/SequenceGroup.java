package org.burlingame.ftc.lib.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SequenceGroup extends Command implements CommandParent {

    private List<Command> children = new LinkedList<>();
    private ListIterator<Command> iterator;
    private Command current;
    private boolean finished = false;

    SequenceGroup(Command first, Command second) {
        children.add(first);
        children.add(second);
        first.parent = this;
        second.parent = this;
        require(first.requiredSubsystems);
        require(second.requiredSubsystems);
    }

    @Override
    public SequenceGroup then(Command other) {
        children.add(other);
        other.parent = this;
        require(other.requiredSubsystems);
        return this;
    }

    @Override
    protected void init() {
        iterator = children.listIterator();
        current = iterator.next();
        finished = false;
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }

    @Override
    public void onCommandFinish(Command command, boolean interrupted) {
        if (command != current) {
            throw new IllegalStateException("onCommandFinish was called on the wrong command!");
        }
        if (iterator.hasNext()) {
            current = iterator.next();
        } else {
            finished = true;
        }
    }
}
