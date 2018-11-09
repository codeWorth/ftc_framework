package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.commands.Command;

public class DriveToCorridor extends Command {

    DriveDistance drive;

    @Override
    public void init() {
        double dist = Constants.CORRIDOR_DISTANCE + Constants.RETURN_SPACING * Math.sin(-TurnTowardsCheddar.ANGLE_TURNED / 180 * Math.PI);
        drive = new DriveDistance((int) dist);
        drive.init();
    }

    @Override
    public void loop() {
        drive.loop();
    }

    @Override
    public boolean shouldRemove() {
        return drive.shouldRemove();
    }

    @Override
    protected void end() {
        drive.end();
    }
}
