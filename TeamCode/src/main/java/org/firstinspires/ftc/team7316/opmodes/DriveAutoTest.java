package org.firstinspires.ftc.team7316.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.commands.DriveDistance;
import org.firstinspires.ftc.team7316.commands.TurnGyro;
import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.GyroAngles;
import org.firstinspires.ftc.team7316.util.GyroWrapper;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.Scheduler;
import org.firstinspires.ftc.team7316.util.modes.AutoBaseOpMode;
@Autonomous(name="Auto")
public class DriveAutoTest extends AutoBaseOpMode {

    double dps = 0;
    int count = 0;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void onInit() {
        Scheduler.instance.add(new TurnGyro(90));
        Hardware.instance.gyroWrapper.resetHeading();
        timer.seconds();
    }

    @Override
    public void onLoop() {

    }
}
