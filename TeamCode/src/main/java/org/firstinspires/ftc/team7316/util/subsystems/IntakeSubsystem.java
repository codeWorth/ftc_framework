package org.firstinspires.ftc.team7316.util.subsystems;

import org.firstinspires.ftc.team7316.commands.TeleopIntake;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;

public class IntakeSubsystem extends Subsystem {
    @Override
    public void reset() {
        Hardware.instance.intakemotor.setPower(0);
    }

    @Override
    public Command defaultAutoCommand() {
        return null;
    }

    @Override
    public Command defaultTeleopCommand() {
        return new TeleopIntake();
    }
    public void setMotor(double power){
        Hardware.instance.intakemotor.setPower(power);
    }
}
