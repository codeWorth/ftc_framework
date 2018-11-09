package org.firstinspires.ftc.team7316.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.commands.TurnGyroSimple;
import org.firstinspires.ftc.team7316.util.GyroAngles;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.Scheduler;
import org.firstinspires.ftc.team7316.util.modes.AutoBaseOpMode;
@Autonomous(name="Auto turn test")
public class DriveTurnTest extends AutoBaseOpMode {

    double dps = 0;
    int count = 0;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void onInit() {
        Scheduler.instance.add(new TurnGyroSimple(90));
        Hardware.instance.gyroWrapper.resetHeading(Hardware.instance.gyroWrapper.angles().yaw);
        timer.reset();
    }

    @Override
    public void onLoop() {

        GyroAngles angles = Hardware.instance.gyroWrapper.angles();
//        Hardware.instance.gyroWrapper.resetHeading(angles.yaw);

        dps += Math.abs(angles.heading) / timer.seconds();
        timer.reset();
        count++;

        Hardware.log("dps", dps / count);
    }
}
