package org.firstinspires.ftc.team7316.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.Scheduler;
import org.firstinspires.ftc.team7316.util.commands.AutoCodes;
import org.firstinspires.ftc.team7316.util.modes.AutoBaseOpMode;

@Autonomous(name="Auto Short")
public class AutoShortPath extends AutoBaseOpMode {

    @Override
    public void onInit() {
//        Scheduler.instance.add(new TurnGyroSimple(90));
        Scheduler.instance.add(AutoCodes.SingleCheddarLandingZoneSide());
    }

    @Override
    public void onLoop() {
    }

}
