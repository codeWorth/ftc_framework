package org.firstinspires.ftc.team7316.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.commands.TurnGyroSimple;
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
    long ticks = 0;
    long ticks = 0;
    long ticks2 = 0;

    double power = 1.1;

    @Override
    public void onInit() {
        Scheduler.instance.add(new TurnGyroSimple(90));
        Hardware.instance.gyroWrapper.resetHeading(Hardware.instance.gyroWrapper.angles().yaw);
        timer.reset();
        timer2.reset();

        ticks = Hardware.instance.leftmotor.getCurrentPosition();
    }

    @Override
    public void onLoop() {

////        if (timer.seconds() < 2) {
////            ticks = Hardware.instance.leftmotor.getCurrentPosition();
////        } else if (timer.seconds() < 4) {
////            ticks2 = Hardware.instance.leftmotor.getCurrentPosition();
////        } else {
////            Hardware.log("ticks", ticks2 - ticks);
////        }
////
////        Hardware.instance.leftmotor.setPower(0.75);
////        Hardware.instance.rightmotor.setPower(0.75);
//
//        GyroAngles angles = Hardware.instance.gyroWrapper.angles();
//        Hardware.instance.gyroWrapper.resetHeading(angles.yaw);
//
//        dps += Math.abs(angles.heading) / timer.seconds();
//        timer.reset();
//        count++;
//
//        double dp = -0.1;
//        //double power = 80 * Constants.TURN_F;
//
////        Hardware.log(String.valueOf(power), dps / count);
////        Hardware.log("expected speed " + String.valueOf(power), power / Constants.TURN_F);
//
//        Hardware.instance.rightmotor.setPower(-power);
//        Hardware.instance.leftmotor.setPower(power);
//        Hardware.instance.centermotor.setPower(-power);
//
//        Hardware.log("power", power);
//
//        if (timer2.seconds() > 5) {
//            Log.d("used power", String.valueOf(power));
//            Log.d("real speed", String.valueOf(dps / count));
//            dps = 0;
//            count = 0;
//            power += dp;
//            timer2.reset();
//        }
//
////        double x = 0;
////
////        if (CameraUntilCheddar.contour != null) {
////            Moments M = Imgproc.moments(CameraUntilCheddar.contour);
////            x = Math.floor(M.m01 /  M.m00);
////        }
////
////        Hardware.log("cheddar x", x);
    }

}
