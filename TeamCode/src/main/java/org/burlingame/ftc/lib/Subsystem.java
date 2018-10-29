package org.burlingame.ftc.lib;

import org.burlingame.ftc.lib.commands.Command;

import java.util.Stack;

public abstract class Subsystem {

    private boolean initializedDefaultCommand_t;
    private boolean initializedDefaultCommand_a;
    private Command defaultCommand = null;

    private Stack<Command> currentCommandQueue = new Stack();

    public Command currentCommand() {
        if (currentCommandQueue.empty()) {
            return null;
        } else {
            return currentCommandQueue.peek();
        }
    }

    public void replaceCurrentCommand(Command newCmd) {
        boolean oldCommandDone = this.currentCommand().pause();
        if (oldCommandDone) {
            this.currentCommandQueue.pop();
        }
        this.currentCommandQueue.add(newCmd);
    }

    public void currentCommandEnded() {
        this.currentCommandQueue.pop();
        while (!this.currentCommand().canResume() || this.currentCommand()._isFinished()) {
            this.currentCommandQueue.pop();
        }
    }

    public Subsystem() {
        Scheduler.getInstance().registerSubsystem(this);
    }

    public Command getDefaultCommand() {
        if (Scheduler.getInstance().inTeleop && !initializedDefaultCommand_t) {
            Command wanted = defaultTeleopCommand();
            testCommand(wanted);
            defaultCommand = wanted;
            initializedDefaultCommand_t = true;
        } else if (!Scheduler.getInstance().inTeleop && !initializedDefaultCommand_a) {
            Command wanted = defaultAutoCommand();
            testCommand(wanted);
            defaultCommand = wanted;
            initializedDefaultCommand_a = true;
        }
        return defaultCommand;
    }

    private void testCommand(Command cmd) {
        if (cmd == null) {
            return;
        }

        int reqs = cmd.requiredSubsystems.size();
        if (reqs == 0 || reqs > 1) {
            throw new IllegalArgumentException("Default command must require exactly 1 subsystem");
        } else {
            Subsystem sub = cmd.requiredSubsystems.get(0);
            if (sub != this) {
                throw new IllegalArgumentException("Default command must require its subsystem");
            }
        }
    }

    protected abstract Command defaultAutoCommand();
    protected abstract Command defaultTeleopCommand();

}
