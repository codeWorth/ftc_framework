package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.GyroAngles;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.copypastaLib.CombinedPath;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class TurnGyro extends Command {

    private int deltaHeading;
    private CombinedPath.LongitudalTrapezoid anglePath;
    private long startTime;
    private double lastError;
    private double sumError;
    private long lastTime;

    public TurnGyro(int deltaHeading) {
        // prevents the robot from turning in a stupid way
        int modA = (deltaHeading - 180) % 360;
        if (modA < 0) {
            modA += 360;
        }
        modA -= 180;
        this.deltaHeading = modA;

        if (this.deltaHeading >= 0) {
            anglePath = new CombinedPath.LongitudalTrapezoid(0, this.deltaHeading, Constants.MAX_DEGREES_SPEED, Constants.MAX_DEGREES_ACCEL);
        } else {
            anglePath = new CombinedPath.LongitudalTrapezoid(0, this.deltaHeading, -Constants.MAX_DEGREES_SPEED, -Constants.MAX_DEGREES_ACCEL);
        }
    }

    @Override
    public void init() {
        startTime = System.currentTimeMillis();
        lastTime = startTime;
        Hardware.instance.gyroWrapper.resetHeading();
    }

    @Override
    public void loop() {
        long milis = System.currentTimeMillis() - startTime;
        long dMilis = System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        double dSeconds = (double)dMilis / 1000.0;
        double seconds = (double)milis / 1000.0;

        double degreesPerSec = this.anglePath.getSpeed(seconds);

        GyroAngles angles = Hardware.instance.gyroWrapper.angles();
        double error = angles.heading - this.anglePath.getPosition(seconds);
        sumError += error;
        double dError = (error - lastError) / dSeconds;

        double power = Constants.TURN_P * error + Constants.TURN_I * sumError + Constants.TURN_D * dError + Constants.TURN_F * degreesPerSec;

        Hardware.instance.leftmotorWrapper.setPower(power);
        Hardware.instance.leftmotorWrapper.setPower(-power);

    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    protected void end() {

    }
}
