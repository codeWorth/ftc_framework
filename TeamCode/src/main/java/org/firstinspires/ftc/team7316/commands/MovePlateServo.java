package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystem;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class MovePlateServo extends Command {

    boolean lower;

    /**
     * Lower or Raise the plate servo
     * @param lower True if lower, false if raise
     */
    public MovePlateServo(boolean lower) {
        this.lower = lower;
    }

    @Override
    public void init() {
        if (lower) {
            Subsystems.instance.plateSubsystem.servoLower();
        } else {
            Subsystems.instance.plateSubsystem.servoRaise();
        }
    }

    @Override
    public void loop() {

    }

    @Override
    public boolean shouldRemove() {
        return true;
    }

    @Override
    protected void end() {

    }
}
