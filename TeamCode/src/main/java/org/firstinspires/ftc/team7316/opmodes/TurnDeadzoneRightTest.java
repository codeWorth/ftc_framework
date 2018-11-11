package org.firstinspires.ftc.team7316.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.modes.AutoBaseOpMode;

@Autonomous(name="Test Right Turn Deadzone")
public class TurnDeadzoneRightTest extends AutoBaseOpMode {

    double power = 0.4;
    double dp = -0.05;
    ElapsedTime powerTimer = new ElapsedTime();
    double minPower = -1;

    @Override
    public void onInit() {
        powerTimer.reset();
    }

    @Override
    public void onLoop() {
        Hardware.instance.rightmotor.setPower(-power);
        Hardware.instance.leftmotor.setPower(power);
        Hardware.instance.centermotor.setPower(-power);

        Hardware.log("power", power);
        if (minPower != -1) {
            Hardware.log("left deadzone", minPower);
        }

        if (powerTimer.seconds() > 3 && power > 0) {
            if (Math.abs(Hardware.instance.imu.getAngularVelocity().yRotationRate) < 0.01 && minPower == -1) {
                minPower = Math.abs(power);
            }

            power += dp;
            powerTimer.reset();
        }
    }

}
