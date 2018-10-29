package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.copypastaLib.CombinedPath;

public class TurnGyro extends Command {

    private int deltaHeading;
    private CombinedPath.LongitudalTrapezoid anglePath;

    public TurnGyro(int deltaHeading) {
        // prevents the robot from turning in a stupid way
        int modA = (deltaHeading - 180) % 360;
        if (modA < 0) {
            modA += 360;
        }
        modA -= 180;
        this.deltaHeading = modA;
        anglePath = new CombinedPath.LongitudalTrapezoid(0,this.deltaHeading,Constants.MAX_RADIANS_SPEED, Constants.MAX_RADIANS_ACCEL);
    }

    @Override
    public void init() {
        Hardware.instance.gyroWrapper.resetHeading();
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
