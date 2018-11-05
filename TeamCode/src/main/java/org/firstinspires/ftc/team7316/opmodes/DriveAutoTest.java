package org.firstinspires.ftc.team7316.opmodes;

import android.util.Log;

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
    ElapsedTime timer2 = new ElapsedTime();

    double power = 0.1;

    @Override
    public void onInit() {
//        Scheduler.instance.add(new TurnGyro(90));
        Hardware.instance.gyroWrapper.resetHeading(Hardware.instance.gyroWrapper.angles().yaw);
        timer.reset();
        timer2.reset();
    }

    @Override
    public void onLoop() {

        GyroAngles angles = Hardware.instance.gyroWrapper.angles();
        Hardware.instance.gyroWrapper.resetHeading(angles.yaw);

        dps += Math.abs(angles.heading) / timer.seconds();
        timer.reset();
        count++;

        double dp = 0.1;
        //double power = 80 * Constants.TURN_F;

        Hardware.log(String.valueOf(power), dps / count);
        Hardware.log("expected speed " + String.valueOf(power), power / Constants.TURN_F);

        Hardware.instance.rightmotor.setPower(-power);
        Hardware.instance.leftmotor.setPower(power);
        Hardware.instance.centermotor.setPower(-power);

        if (timer2.seconds() > power * 50) {
            Log.d("used power", String.valueOf(power));
            Log.d("expected speed", String.valueOf(power / Constants.TURN_F));
            Log.d("real speed", String.valueOf(dps / count));
            dps = 0;
            count = 0;
            power += dp;
        }


    }

}
