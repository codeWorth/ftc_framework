package org.burlingame.ftc.lib.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Sequential extends Command {

    private List<Command> children = new LinkedList<>();
    private ListIterator<Command> iterator;
    private Command current;
    private boolean finished = false;

    Sequential(Command... commands) {
        for (Command cmd: commands) {
            addCommand(cmd);
        }
    }

    public static Command seq(Command... commands) {
        return new Sequential(commands);
    }

    private void addCommand(Command other) {
        children.add(other);
        require(other.requiredSubsystems);
    }

    @Override
    public Sequential then(Command other) {
        addCommand(other);
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
        if (current.run()) {
            current._end();
            iterator.remove();
            if (iterator.hasNext()) {
                current = iterator.next();
            } else {
                finished = true;
            }
        }
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
        current._interrupted();
    }
}
