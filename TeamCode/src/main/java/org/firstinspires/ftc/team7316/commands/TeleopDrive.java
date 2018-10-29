package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.input.OI;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class TeleopDrive extends Command {
    double leftset=0;
    double rightset=0;
    double centerset=0;
    double leftchange=0;
    double rightchange=0;
    double centerchange=0;
    double leftlast=0;
    double rightlast=0;
    double centerlast=0;
    boolean fast;
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        if(OI.instance.gp1.rightTriggerWrapper.pressedState()){
            fast= true;
        }
        else {
            fast=false;
        }
        leftchange= Hardware.instance.leftmotor.getCurrentPosition()-leftlast;
        rightchange=Hardware.instance.rightmotor.getCurrentPosition()-rightlast;
        centerchange=Hardware.instance.centermotor.getCurrentPosition()-centerlast;
        leftlast=Hardware.instance.leftmotor.getCurrentPosition();
        rightlast=Hardware.instance.rightmotor.getCurrentPosition();
        centerlast=Hardware.instance.centermotor.getCurrentPosition();
        double leftTarget = OI.instance.gp1.left_stick.getY();
        double rightTarget = OI.instance.gp1.left_stick.getY();
        leftTarget+=OI.instance.gp1.right_stick.getX();
        rightTarget-=OI.instance.gp1.right_stick.getX();
        double centerTarget=OI.instance.gp1.left_stick.getX();
        if (leftTarget>1){
            leftTarget=1;
        }
        if (leftTarget<-1){
            leftTarget=-1;
        }
        if (rightTarget>1){
            rightTarget=1;
        }
        if (rightTarget<-1){
            rightTarget=-1;
        }
        if (centerTarget>1){
            centerTarget=1;
        }
        if (centerTarget<-1){
            centerTarget=-1;
        }
        if(Math.abs(leftset)<Math.abs(leftTarget)){
            leftset+=(Math.abs(leftTarget)/leftTarget)* Constants.ACCELERATION_SPEED;
        }
        if (Math.abs(rightset)<Math.abs(rightTarget)){
            rightset+=(Math.abs(rightTarget)/rightTarget)* Constants.ACCELERATION_SPEED;
        }
        if (Math.abs(centerset)<Math.abs(centerTarget)){
            centerset+=(Math.abs(centerTarget)/centerTarget)* Constants.ACCELERATION_SPEED;
        }
        if (leftset>leftTarget){
            leftset=leftTarget;
        }
        if (rightset>rightTarget){
            rightset=rightTarget;
        }
        if(centerset>centerTarget){
            centerset=centerTarget;
        }
        if (fast==false){
            leftset*=Constants.SLOW_SPEED;
            rightset*=Constants.SLOW_SPEED;
            centerset*=Constants.SLOW_SPEED;
        }
        Subsystems.instance.driveSubsystem.driveMotorSet(-leftset,rightset);
        Subsystems.instance.driveSubsystem.strafeMotorSet(centerset);
    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    protected void end() {

    }
}
