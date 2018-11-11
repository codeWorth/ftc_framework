package org.firstinspires.ftc.team7316.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.modes.AutoBaseOpMode;

import java.util.ArrayList;

@Autonomous(name="Test Left Turn Deadzone")
public class TurnDeadzoneLeftTest extends AutoBaseOpMode {

    double power = -0.4;
    double dp = 0.05;
    ElapsedTime powerTimer = new ElapsedTime();
    double rotRate = 0;
    int count = 0;

    double m = -1;
    double b = -1;

    ArrayList<Double> rotRates = new ArrayList<>();
    ArrayList<Double> powers = new ArrayList<>();

    @Override
    public void onInit() {
        powerTimer.reset();
    }

    @Override
    public void onLoop() {
        Hardware.instance.rightmotor.setPower(-power);
        Hardware.instance.leftmotor.setPower(power);
        Hardware.instance.centermotor.setPower(-power);

        rotRate += Hardware.instance.imu.getAngularVelocity().yRotationRate;
        count++;

        Hardware.log("power", power);

        if (b != -1) {
            Hardware.log("turn deadzone", Math.abs(b));
        }

        if (powerTimer.seconds() > 3 && power > 0.05) {

            if (Math.abs(Hardware.instance.imu.getAngularVelocity().yRotationRate) < 0.01 && b == -1) {
                // input is speed (x), output is power (y), b is value of power when speed = 0

                int n = rotRates.size();
                rotRates.remove(n-1); // last val might be bad, no reason to use it
                powers.remove(n-1);
                n--;

                double xAvg = 0;
                double yAvg = 0;
                for (double x : rotRates) {
                    xAvg += x;
                }
                for (double y : powers) {
                    yAvg += y;
                }
                xAvg = xAvg / count;
                yAvg = xAvg / count;

                double stdX = 0;
                double stdY = 0;
                for (double x : rotRates) {
                    stdX += (x - xAvg) * (x - xAvg);
                }
                for (double y : powers) {
                    stdX += (y - yAvg) * (y - yAvg);
                }
                stdX = Math.sqrt(stdX / (count - 1));
                stdY = Math.sqrt(stdY / (count - 1));

                double r = 0;
                for (int i = 0; i < count; i++) {
                    double x = rotRates.get(i);
                    double y = powers.get(i);
                    r += x * y;
                }
                r = (r - n * xAvg * yAvg) / (stdX * stdY * (count - 1));

                m = r * stdY / stdX;
                b = yAvg - m * xAvg;
            }

            rotRates.add(rotRate / count);
            powers.add(power);

            count = 0;
            rotRate = 0;
            power += dp;
            powerTimer.reset();


        }
    }

}
