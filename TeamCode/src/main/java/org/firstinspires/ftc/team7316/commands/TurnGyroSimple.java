package org.firstinspires.ftc.team7316.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.GyroAngles;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;

import java.net.HttpRetryException;

public class TurnGyroSimple extends Command {

    double power = 0.7;
    int deltaHeading = 0;
    public GyroAngles angles;
    ElapsedTime timer = new ElapsedTime();

    public TurnGyroSimple(int deltaHeading) {
        this(deltaHeading, 0.6);
    }

    public TurnGyroSimple(int deltaHeading, double power) {
        // prevents the robot from turning in a stupid way
        int modA = (deltaHeading - 180) % 360;
        if (modA < 0) {
            modA += 360;
        }
        modA -= 180;
        this.deltaHeading = modA;
        this.power = power;
    }

    @Override
    public void init() {
        GyroAngles inititalAngles = Hardware.instance.gyroWrapper.angles();
        Hardware.instance.gyroWrapper.resetHeading(inititalAngles.yaw);
        inititalAngles.heading = 0;
        this.angles = inititalAngles;
        timer.reset();
    }

    @Override
    public void loop() {
        double realPower = this.power * Constants.TURN_P * (this.deltaHeading - angles.heading); //Math.signum(this.deltaHeading - angles.heading);
        realPower += Math.signum(realPower) * 0.15;

        if (Math.abs(realPower) > 0.4) {
            realPower = 0.4 * Math.signum(realPower);
        }

        GyroAngles deltaAngles = Hardware.instance.gyroWrapper.angles();
        this.angles.add(deltaAngles);
        Hardware.instance.gyroWrapper.resetHeading(deltaAngles.yaw);

        Hardware.log("power", realPower);
        Hardware.log("wanted", this.deltaHeading);
        Hardware.log("current", angles.heading);

        Hardware.instance.leftmotor.setPower(realPower);
        Hardware.instance.rightmotor.setPower(-realPower);
        Hardware.instance.centermotor.setPower(-realPower);
    }

    @Override
    public boolean shouldRemove() {
        return timer.seconds() > 3 || (Math.abs(this.deltaHeading - angles.heading) < 2 && Math.abs(Hardware.instance.imu.getAngularVelocity().yRotationRate) < 0.2);
    }

    @Override
    protected void end() {
        Hardware.instance.leftmotor.setPower(0);
        Hardware.instance.rightmotor.setPower(0);
        Hardware.instance.centermotor.setPower(0);
    }
}
