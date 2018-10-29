package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.input.OI;
import org.firstinspires.ftc.team7316.util.subsystems.ClimberSubsystem;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class TeleopClimb extends Command {
    @Override
    public void init() {
    }

    @Override
    public void loop() {
        if(OI.instance.gp2.dp_down.pressedState()) {
            Subsystems.instance.climberSubsystem.setMotor(-1);
        }
        if(OI.instance.gp2.dp_up.pressedState()) {
            Subsystems.instance.climberSubsystem.setMotor(1);
        }
    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    protected void end() {

    }
}
