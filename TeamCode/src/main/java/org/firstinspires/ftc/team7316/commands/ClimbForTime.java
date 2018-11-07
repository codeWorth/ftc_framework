package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystem;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class ClimbForTime extends Command {
    int time;
    long startingTime;
    boolean up;
    public ClimbForTime(int time, boolean up){
        this.time=time;
        this.up=up;
    }
    @Override
    public void init() {
        startingTime=System.currentTimeMillis();
    }
    @Override
    public void loop() {
        if(up==true) {
            Subsystems.instance.climberSubsystem.setMotor(1);
        }
        else {
            Subsystems.instance.climberSubsystem.setMotor(-1);
        }
        if(Hardware.instance.climbmotor.getCurrentPosition()> Constants.CLIMB_MOTOR_MAX||Hardware.instance.climbmotor.getCurrentPosition()<Constants.CLIMB_MOTOR_MIN){
            Subsystems.instance.climberSubsystem.setMotor(0);
        }
    }

    @Override
    public boolean shouldRemove() {
        return startingTime-System.currentTimeMillis()>time;
    }

    @Override
    protected void end() {
        Subsystems.instance.climberSubsystem.setMotor(0);
    }
}
