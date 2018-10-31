package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.copypastaLib.CombinedPath;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class TurnEncoder extends Command {
    int leftTicks;
    int rightTicks;

    int ticks = 0;

    long lastTime;

    CombinedPath.LongitudalTrapezoid path;

    public TurnEncoder(int angle){
        // prevents the robot from turning in a stupid way
        int modA = (angle - 180) % 360;
        if (modA < 0) {
            modA += 360;
        }
        modA -= 180;

        ticks = Constants.degreesToTicks(modA);

        if (this.ticks >= 0) {
            path = new CombinedPath.LongitudalTrapezoid(0, this.ticks, Constants.MAX_TICKS_SPEED, Constants.MAX_TICKS_ACCEL);
        } else {
            path = new CombinedPath.LongitudalTrapezoid(0, this.ticks, -Constants.MAX_TICKS_SPEED, -Constants.MAX_TICKS_ACCEL);
        }
    }

    @Override
    public void init() {
        Subsystems.instance.driveSubsystem.resetMotors();
        if(ticks>0){
            Subsystems.instance.driveSubsystem.setMotorPaths(new CombinedPath.LongitudalTrapezoid(0,ticks,1500,1500));
        }
        else {
            Subsystems.instance.driveSubsystem.setMotorPaths((new CombinedPath.LongitudalTrapezoid(0,ticks,-1500,-1500)));
        }
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void loop(){
        long dMilis = System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        Subsystems.instance.driveSubsystem.driveWithPID((double)dMilis / 1000.0);
    }

    @Override
    public boolean shouldRemove() {
        return Subsystems.instance.driveSubsystem.checkMotorsFinished();
    }

    @Override
    protected void end() {
        Subsystems.instance.driveSubsystem.driveMotorSet(0,0);
    }
}
