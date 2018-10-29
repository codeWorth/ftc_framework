package org.burlingame.ftc.lib.subsystem;

import org.burlingame.ftc.lib.Scheduler;
import org.burlingame.ftc.lib.commands.Command;

import java.util.ArrayList;

public abstract class Subsystem {

    private boolean initializedDefaultCommand_t;
    private boolean initializedDefaultCommand_a;
    private Command defaultCommand = null;

    private ArrayList<Command> currentCommandQueue = new ArrayList<>();

    public Command currentCommand() {
        if (currentCommandQueue.isEmpty()) {
            return null;
        } else {
            return currentCommandQueue.get(0);
        }
    }

    public void replaceCurrentCommand(Command newCmd) {
        boolean oldCommandDone = this.currentCommand().pause();
        if (oldCommandDone && !this.currentCommandQueue.isEmpty()) {
            this.currentCommandQueue.remove(0);
        }
        this.currentCommandQueue.add(0, newCmd);
    }

    public void currentCommandEnded() {
        if (!this.currentCommandQueue.isEmpty()) {
            this.currentCommandQueue.remove(0);
        }
        while (!currentCommandQueue.isEmpty() && (!this.currentCommand().canResume() || this.currentCommand()._isFinished())) {
            this.currentCommandQueue.remove(0);
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
