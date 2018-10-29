package org.firstinspires.ftc.team7316.util.subsystems;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;

public class PlateSubsystem extends Subsystem {
    @Override
    public void reset() {
    }

    @Override
    public Command defaultAutoCommand() {
        return null;
    }
    public void servoLower(){
        Hardware.instance.plateServo.setPosition(1);
    }

    @Override
    public Command defaultTeleopCommand() {
        return null;
    }
}
