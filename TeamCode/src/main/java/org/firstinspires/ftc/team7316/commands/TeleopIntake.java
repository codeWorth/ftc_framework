package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.input.OI;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class TeleopIntake extends Command {

    @Override
    public void init() {
        requires(Subsystems.instance.intakeSubsystem);
    }

    @Override
    public void loop() {

        double power = OI.instance.gp2.left_stick.getY();
        Hardware.instance.intakeServo.setPower(power);

    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    protected void end() {

    }
}
