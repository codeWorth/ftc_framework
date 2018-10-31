package org.firstinspires.ftc.team7316.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.GyroAngles;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.input.OI;
import org.firstinspires.ftc.team7316.util.modes.TeleopBaseOpMode;

@TeleOp(name="Teleop")
public class TeleopDriveOpMode extends TeleopBaseOpMode {

    long lastTime;
    long lastTicks;
    double avSpeed;
    int count;

    @Override
    public void onInit() {
        lastTime = System.currentTimeMillis();
        lastTicks = Hardware.instance.leftmotor.getCurrentPosition();
    }

    @Override
    public void onLoop() {

        Hardware.log("dtime", String.valueOf(System.currentTimeMillis() - lastTime));

        if (OI.instance.gp1.a_button.state()) {

            long dTimeMilis = System.currentTimeMillis() - lastTime;
            double dTicks = (double) (Hardware.instance.leftmotor.getCurrentPosition() - lastTicks);
            lastTime = System.currentTimeMillis();
            lastTicks = Hardware.instance.leftmotor.getCurrentPosition();

            double dTime = ((double) dTimeMilis) / 1000.0;

            avSpeed += dTicks / dTime;
            count++;
        } else {
            lastTime = System.currentTimeMillis();
        }

        Hardware.log("sped", avSpeed / count);
        Hardware.log("ticks left", Hardware.instance.leftmotor.getCurrentPosition());
        Hardware.log("ticks calc", Constants.degreesToTicks(360));


    }
}
