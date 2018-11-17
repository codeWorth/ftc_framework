package org.burlingame.ftc.lib.commands;

import org.burlingame.ftc.lib.subsystem.Subsystem;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

public class Parallel extends Command {

    private LinkedList<Command> children = new LinkedList<>();
    private LinkedList<Command> running = new LinkedList<>();
    private boolean any;
    private int finishSize;

    private Parallel(boolean any, Command... commands) {
        Collections.addAll(this.children, commands);
        for (Command command: commands) {
            for (Subsystem sub : command.requiredSubsystems) {
                if (requiredSubsystems.contains(sub)) {
                    throw new IllegalArgumentException("Parallel commands should not require the same subsystem!");
                }
                require(sub);
            }
        }
        this.any = any;
    }

    public static Command any(Command... cmds) {
        return new Parallel(true, cmds);
    }

    public static Command all(Command... cmds) {
        return new Parallel(false, cmds);
    }

    @Override
    protected void init() {
        running.addAll(children);
        finishSize = any ? running.size() - 1 : 0;
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
        return running.size() <= finishSize;
    }

    @Override
    protected void end() {
        interruptRemaining();
    }

    @Override
    protected void interrupted() {
        interruptRemaining();
    }

    private void interruptRemaining() {
        for (Command cmd: running) {
            cmd._interrupted();
        }
        running.clear();
    }

}
