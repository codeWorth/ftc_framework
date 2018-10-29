package org.firstinspires.ftc.team7316.util.subsystems;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;

public class ClimberSubsystem extends Subsystem {
    @Override
    public void reset() {
        Hardware.instance.climbmotor.setPower(0);
    }

    @Override
    public Command defaultAutoCommand() {
        return null;
    }

    @Override
    public Command defaultTeleopCommand() {
        return null;
    }
    public void setMotor(double power){
        Hardware.instance.climbmotor.setPower(power);
    }
}
