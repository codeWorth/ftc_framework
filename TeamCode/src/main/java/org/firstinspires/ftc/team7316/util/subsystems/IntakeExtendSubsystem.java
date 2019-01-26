package org.firstinspires.ftc.team7316.util.subsystems;

import org.firstinspires.ftc.team7316.commands.TeleopIntakeExtend;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;

public class IntakeExtendSubsystem extends Subsystem {
    @Override
    public void reset() {
        Hardware.instance.extendmotorleft.setPower(0);
        Hardware.instance.extendmotorright.setPower(0);
    }

    @Override
    public Command defaultAutoCommand() {
        return null;
    }

    @Override
    public Command defaultTeleopCommand() {
        return new TeleopIntakeExtend();
    }
    public void ExtendMotorSet(double power){
        Hardware.instance.extendmotorright.setPower(power);
        Hardware.instance.extendmotorleft.setPower(-power);
    }
}
