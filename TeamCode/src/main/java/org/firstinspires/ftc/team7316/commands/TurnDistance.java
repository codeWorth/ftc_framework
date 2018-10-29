package org.firstinspires.ftc.team7316.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.util.GyroWrapper;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class TurnDistance extends Command {
    public static final double ERROR_THRESHOLD = 4, DELTA_THRESHOLD = 2, MAX_POWER = 1, ACCEL_RATE = 150;
    private double wantedAngle, startAngle, targetAngleCurrent, targetAngleFinal, TURN_TIME, ACCEL_TIME, COAST_TIME, MAX_SPEED;
    private GyroWrapper gyro = Hardware.instance.gyroWrapper;
    public TurnDistance(double wantedAngle) {
        this(wantedAngle, 6);
    }
    private ElapsedTime timer = new ElapsedTime();
    private double previousTime = 0;
    private double timeout;
    private int completedCount = 0;
    private final int countThreshold = 10;
    public TurnDistance(double wantedAngle, double timeout) {
        this(wantedAngle, timeout, 90);
    }

    public TurnDistance(double wantedAngle, double timeout, double maxspeed) {
        requires(Subsystems.instance.driveSubsystem);
        this.MAX_SPEED = maxspeed;

        this.wantedAngle = wantedAngle;
        this.timeout = timeout;

        this.ACCEL_TIME = this.MAX_SPEED / ACCEL_RATE;
        this.COAST_TIME = (wantedAngle - ACCEL_RATE * ACCEL_TIME * ACCEL_TIME) / this.MAX_SPEED;
        this.TURN_TIME = ACCEL_TIME * 2 + COAST_TIME;
    }
    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    protected void end() {

    }
}
