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
        Hardware.instance.plateServo.setPosition(0.6);
    }
    public void servoRaise(){
        Hardware.instance.plateServo.setPosition(0);
    }

    @Override
    public Command defaultTeleopCommand() {
        return null;
    }
}
