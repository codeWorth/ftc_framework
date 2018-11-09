package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.commands.Command;

public class ResetAngle extends Command {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        TurnTowardsCheddar.ANGLE_TURNED = 0;
    }

    @Override
    public boolean shouldRemove() {
        return true;
    }

    @Override
    protected void end() {

    }
}
