package org.firstinspires.ftc.team7316.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.GyroAngles;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.AutoDecision;
import org.firstinspires.ftc.team7316.util.commands.Command;

import java.net.HttpRetryException;

public class TurnGyroSimple extends Command {

    double power = 0.7;
    int deltaHeading = 0;
    public GyroAngles angles;
    ElapsedTime timer = new ElapsedTime();
    private AutoDecision decision = null;
    public double thresh = 2;

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

    public TurnGyroSimple(AutoDecision decision, double angleThresh) {
        this.power = 0.6;
        this.decision = decision;
        thresh = angleThresh;
    }

    public TurnGyroSimple(AutoDecision decision) {
        this(decision, 2);
    }

    @Override
    public void init() {
        if (this.decision != null) {
            int modA = ((int) this.decision.findNumber() - 180) % 360;
            if (modA < 0) {
                modA += 360;
            }
            modA -= 180;
            this.deltaHeading = modA;
        }

        GyroAngles inititalAngles = Hardware.instance.gyroWrapper.angles();
        Hardware.instance.gyroWrapper.resetHeading(inititalAngles.yaw);
        inititalAngles.heading = 0;
        this.angles = inititalAngles;
        timer.reset();
    }

    @Override
    public void loop() {
        double realPower;
        if ((this.deltaHeading - angles.heading) > 0) {
            realPower = this.power * Constants.TURN_P_RIGHT * (this.deltaHeading - angles.heading) + Constants.TURN_DEADZONE_RIGHT;
        } else {
            realPower = this.power * Constants.TURN_P_LEFT * (this.deltaHeading - angles.heading) + Constants.TURN_DEADZONE_LEFT;
        }

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
        return timer.seconds() > 3 || (Math.abs(this.deltaHeading - angles.heading) < this.thresh && Math.abs(Hardware.instance.imu.getAngularVelocity().yRotationRate) < 0.5);
    }

    @Override
    protected void end() {
        Hardware.instance.leftmotor.setPower(0);
        Hardware.instance.rightmotor.setPower(0);
        Hardware.instance.centermotor.setPower(0);
    }
}
