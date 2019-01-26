package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.input.OI;
import org.firstinspires.ftc.team7316.util.subsystems.IntakeExtendSubsystem;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class TeleopIntakeExtend extends Command {
    @Override
    public void init() {
        requires(Subsystems.instance.intakeExtendSubsystem);
        Subsystems.instance.intakeExtendSubsystem.reset();
    }

    @Override
    public void loop() {
        Subsystems.instance.intakeExtendSubsystem.ExtendMotorSet(OI.instance.gp2.left_stick.getY());
    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    protected void end() {

    }
}
