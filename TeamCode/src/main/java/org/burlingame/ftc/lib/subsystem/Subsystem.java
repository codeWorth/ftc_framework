package org.burlingame.ftc.lib.subsystem;

import org.burlingame.ftc.lib.commands.Scheduler;
import org.burlingame.ftc.lib.commands.Command;

public abstract class Subsystem {

    private boolean initializedDefaultCommand_t;
    private boolean initializedDefaultCommand_a;
    private Command defaultCommand = null;

    public Command currentCommand;

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
        } else if (!cmd.requiredSubsystems.contains(this)) {
            throw new IllegalArgumentException("Default command must require its subsystem");
        }
    }

    protected abstract Command defaultAutoCommand();
    protected abstract Command defaultTeleopCommand();

}
