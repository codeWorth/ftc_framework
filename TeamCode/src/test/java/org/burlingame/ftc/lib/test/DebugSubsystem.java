package org.burlingame.ftc.lib.test;

import org.burlingame.ftc.lib.commands.Command;
import org.burlingame.ftc.lib.subsystem.Subsystem;

public class DebugSubsystem extends Subsystem {

    public Command defaultAuto, defaultTeleop;

    @Override
    protected Command defaultAutoCommand() {
        return defaultAuto;
    }

    @Override
    protected Command defaultTeleopCommand() {
        return defaultTeleop;
    }
}
