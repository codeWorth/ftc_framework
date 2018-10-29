package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.copypastaLib.CombinedPath;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class DriveDistance extends Command {
    int ticks;
    public DriveDistance(int ticks){
        this.ticks=ticks;
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

    }

    @Override
    public void loop(){
        Subsystems.instance.driveSubsystem.driveWithPID();
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
