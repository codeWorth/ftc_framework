package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.input.OI;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class TeleopDrive extends Command {

    @Override
    public void init() {
        requires(Subsystems.instance.driveSubsystem);
    }

    @Override
    public void loop() {

        double forward = OI.instance.gp1.left_stick.getY();
        double strafe = OI.instance.gp1.left_stick.getX();
        double rotate = OI.instance.gp1.right_stick.getX();

        Hardware.instance.leftmotorWrapper.setPower(forward + rotate + strafe * 0.1);
        Hardware.instance.rightmotorWrapper.setPower(forward - rotate - strafe * 0.1);
        Hardware.instance.centermotorWrapper.setPower(strafe);

    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    protected void end() {

    }
}
