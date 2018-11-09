package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.commands.Command;

public class TurnTowardsCorridor extends Command {

    TurnGyroSimple turn;

    @Override
    public void init() {
        double angle = 90 - TurnTowardsCheddar.ANGLE_TURNED;
        turn = new TurnGyroSimple((int) angle);
        turn.init();
    }

    @Override
    public void loop() {
        turn.loop();
    }

    @Override
    public boolean shouldRemove() {
        return turn.shouldRemove();
    }

    @Override
    protected void end() {
        turn.end();
    }
}
