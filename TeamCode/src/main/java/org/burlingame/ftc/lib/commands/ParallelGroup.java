package org.burlingame.ftc.lib.commands;

import java.util.LinkedList;
import java.util.List;

public class ParallelGroup extends Command implements CommandParent {

    private List<Command> children = new LinkedList<>();
    private int toComplete;

    ParallelGroup(Command first, Command second) {
        children.add(first);
        children.add(second);

        first.parent = this;
        second.parent = this;

        require(first.requiredSubsystems);
        require(second.requiredSubsystems);
    }

    @Override
    public ParallelGroup whilst(Command other) {
        children.add(other);
        other.parent = this;
        require(other.requiredSubsystems);
        return this;
    }

    @Override
    protected void init() {
        for (Command c : children) {
            Scheduler.getInstance().add(c);
        }
        toComplete = children.size();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return toComplete == 0;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }

    @Override
    public void onCommandFinish(Command command, boolean interrupted) {
        // We will trust that the user is not an idiot and changed the listener before us. I know,
        // potentially bad idea.
        toComplete--;
    }
}
