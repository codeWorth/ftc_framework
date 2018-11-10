package org.firstinspires.ftc.team7316.commands;

import android.util.Log;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;

public class TurnTowardsCorridor extends Command {

    TurnGyroSimple turn;

    @Override
    public void init() {
        for (int i = 0; i < 10; i++) {
            Log.d("angle towards corridor", String.valueOf(TurnTowardsCheddar.ANGLE_TURNED));
        }
        double angle = 90 - TurnTowardsCheddar.ANGLE_TURNED;
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
        turn.end();
    }
}
