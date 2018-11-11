package org.firstinspires.ftc.team7316.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystem;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class StrafeForTime extends Command {

    double seconds = 0;
    ElapsedTime time = new ElapsedTime();
    double power = 0.65;

    /**
     * Strafe for some amount of time at a default power
     * @param time How long to strafe for in seconds
     * @param direction True means positive power, false means negative power
     */
    public StrafeForTime(double time, boolean direction) {
        this(time, direction ? 0.65 : -0.65);
    }

    public StrafeForTime(double time, double power) {
        requires(Subsystems.instance.driveSubsystem);
        if (time <= 0) {
            throw new IllegalArgumentException("bitch ass");
        }

        this.seconds = time;
        this.power = power;
    }

    @Override
    public void init() {
        time.reset();
    }

    @Override
    public void loop() {
        Hardware.instance.leftmotor.setPower(this.power * 0.1);
        Hardware.instance.rightmotor.setPower(this.power * -0.1);
        Subsystems.instance.driveSubsystem.strafeMotorSet(this.power);
    }

    @Override
    public boolean shouldRemove() {
        return time.seconds() > seconds;
    }

    @Override
    protected void end() {
        Subsystems.instance.driveSubsystem.strafeMotorSet(0);
    }
}
