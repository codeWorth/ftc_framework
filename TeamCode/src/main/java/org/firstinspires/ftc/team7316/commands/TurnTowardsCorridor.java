package org.firstinspires.ftc.team7316.commands;

import android.util.Log;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;

public class TurnTowardsCorridor extends Command {

    TurnGyroSimple turn;
    public static double ENDING_ANGLE = 0;

    @Override
    public void init() {
        ENDING_ANGLE = 0;
        double angle = -87 + TurnTowardsCheddar.ANGLE_TURNED;
        turn = new TurnGyroSimple((int) angle);
        turn.init();
    }

    @Override
    public void loop() {
        Hardware.log("dist", TurnTowardsCheddar.ANGLE_TURNED);
        turn.loop();
    }

    @Override
    public boolean shouldRemove() {
        return turn.shouldRemove();
    }

    @Override
    protected void end() {
        ENDING_ANGLE = turn.angles.heading;
        turn.end();
    }
}
