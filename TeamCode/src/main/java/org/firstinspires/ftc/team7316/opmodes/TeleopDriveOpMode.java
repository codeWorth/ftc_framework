package org.firstinspires.ftc.team7316.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.input.OI;
import org.firstinspires.ftc.team7316.util.modes.TeleopBaseOpMode;

@TeleOp(name="Teleop")
public class TeleopDriveOpMode extends TeleopBaseOpMode {

    long lastTime;

    @Override
    public void onInit() {
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void onLoop() {

        Hardware.log("dtime", String.valueOf(System.currentTimeMillis() - lastTime));
        lastTime = System.currentTimeMillis();

    }
}
