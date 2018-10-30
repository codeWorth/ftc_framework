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
//        if(OI.instance.gp1.rightTriggerWrapper.pressedState()){
//            fast= true;
//        }
//        else {
//            fast=false;
//        }
        Hardware.instance.leftmotorWrapper.setPower(OI.instance.gp1.left_stick.getY());
        Hardware.instance.rightmotorWrapper.setPower(OI.instance.gp1.right_stick.getY());


    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    protected void end() {

    }
}
