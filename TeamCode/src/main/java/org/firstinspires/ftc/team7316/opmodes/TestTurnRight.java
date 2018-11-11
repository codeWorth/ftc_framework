package org.firstinspires.ftc.team7316.opmodes;

import org.firstinspires.ftc.team7316.commands.TurnGyroSimple;
import org.firstinspires.ftc.team7316.util.Scheduler;
import org.firstinspires.ftc.team7316.util.modes.AutoBaseOpMode;

public class TestTurnRight extends AutoBaseOpMode {
    @Override
    public void onInit() {
        Scheduler.instance.add(new TurnGyroSimple(90));
    }

    @Override
    public void onLoop() {

    }
}
