package org.firstinspires.ftc.team7316.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.commands.CameraUntilCheddar;
import org.firstinspires.ftc.team7316.commands.TurnGyroSimple;
import org.firstinspires.ftc.team7316.util.GyroAngles;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.Scheduler;
import org.firstinspires.ftc.team7316.util.modes.AutoBaseOpMode;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

@Autonomous(name="Auto")
public class DriveAutoTest extends AutoBaseOpMode {

    double runningAverage1 = 0;
    double runningAverage2 = 0;
    int count = 0;
    ElapsedTime timer = new ElapsedTime();
    ElapsedTime timer2 = new ElapsedTime();

    long ticksLeft = 0;
    long ticksRight = 0;
    double power = 1.1;
    double dp = 0.1;

    @Override
    public void onInit() {
        Scheduler.instance.add(new TurnGyroSimple(90));
        Hardware.instance.gyroWrapper.resetHeading(Hardware.instance.gyroWrapper.angles().yaw);
        timer.reset();
        timer2.reset();

        runningAverage1 = 0;
        runningAverage2 = 0;

        ticksLeft = Hardware.instance.leftmotor.getCurrentPosition();
        ticksRight = Hardware.instance.rightmotor.getCurrentPosition();
    }

    @Override
    public void onLoop() {

//        testDriveF();

    }

    private void testTurnF() {
        dp = -0.1;

        GyroAngles angles = Hardware.instance.gyroWrapper.angles();
        Hardware.instance.gyroWrapper.resetHeading(angles.yaw);

        runningAverage1 += Math.abs(angles.heading) / timer.seconds();
        timer.reset();
        count++;

        Hardware.instance.rightmotor.setPower(-power);
        Hardware.instance.leftmotor.setPower(power);
        Hardware.instance.centermotor.setPower(-power);

        Hardware.log("power", power);

        if (timer2.seconds() > 5) {
            Log.d("used power", String.valueOf(power));
            Log.d("real speed", String.valueOf(runningAverage1 / count));
            runningAverage1 = 0;
            count = 0;
            power += dp;
            timer2.reset();
        }
    }

    private void testDriveF() {
        dp = -0.1;

        runningAverage1 += Math.abs(Hardware.instance.leftmotor.getCurrentPosition() - ticksLeft) / timer.seconds();
        ticksLeft = Hardware.instance.leftmotor.getCurrentPosition();
        runningAverage1 += Math.abs(Hardware.instance.rightmotor.getCurrentPosition() - ticksRight) / timer.seconds();
        ticksRight = Hardware.instance.rightmotor.getCurrentPosition();
        timer.reset();
        count++;
        
        Hardware.instance.rightmotor.setPower(0.9 * power);
        Hardware.instance.leftmotor.setPower(power);

        Hardware.log("right power", 0.9 * power);
        Hardware.log("left power", power);

        if (timer2.seconds() > 5) {
            Log.d("used power left", String.valueOf(power));
            Log.d("real speed left", String.valueOf(runningAverage1 / count));
            Log.d("used power right", String.valueOf(0.9 * power));
            Log.d("real speed right", String.valueOf(runningAverage2 / count));
            runningAverage1 = 0;
            runningAverage2 = 0;
            count = 0;
            power += dp;
            timer2.reset();
        }
    }

    private void testCamera() {
        double x = 0;

        if (CameraUntilCheddar.contour != null) {
            Moments M = Imgproc.moments(CameraUntilCheddar.contour);
            x = Math.floor(M.m01 /  M.m00);
        }

        Hardware.log("cheddar x", x);
    }

}
