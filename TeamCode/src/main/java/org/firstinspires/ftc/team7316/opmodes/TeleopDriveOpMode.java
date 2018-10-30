package org.firstinspires.ftc.team7316.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.input.OI;
import org.firstinspires.ftc.team7316.util.modes.TeleopBaseOpMode;

public class TeleopDriveOpMode extends TeleopBaseOpMode {

    long lastTime;
    long lastTicks;

    double avSpeed = 0;
    int count = 0;

    @Override
    public void onInit() {
        lastTime = System.currentTimeMillis();
        lastTicks = Hardware.instance.leftmotor.getCurrentPosition();
    }

    @Override
    public void onLoop() {

        if (OI.instance.gp1.a_button.state()) {

            long dTimeMilis = System.currentTimeMillis() - lastTime;
            double dTicks = (double) (Hardware.instance.leftmotor.getCurrentPosition() - lastTicks);
            lastTime = System.currentTimeMillis();
            lastTicks = Hardware.instance.leftmotor.getCurrentPosition();

            double dTime = ((double) dTimeMilis) / 1000.0;

            avSpeed += dTicks / dTime;
            count++;
        }

        Hardware.log("sped", avSpeed / count);
    }
}
