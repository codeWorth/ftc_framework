package org.firstinspires.ftc.team7316.util.subsystems;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.commands.TeleopDrive;
import org.firstinspires.ftc.team7316.util.copypastaLib.MotionPath;


public class DriveSubsystem extends Subsystem {
    @Override
    public void reset() {
        Hardware.instance.leftmotor.setPower(0);
        Hardware.instance.rightmotor.setPower(0);
        Hardware.instance.centermotor.setPower(0);
    }

    @Override
    public Command defaultAutoCommand() {
        return null;
    }
    TeleopDrive teleopDrive;
    @Override
    public Command defaultTeleopCommand() {
        return new TeleopDrive();
    }
    public void driveMotorSet(double leftset, double rightset){
        Hardware.instance.leftmotor.setPower(leftset);
        Hardware.instance.rightmotor.setPower(rightset);
    }
    public void driveWithPID(){
        Hardware.instance.rightmotorWrapper.setPowerPID();
        Hardware.instance.leftmotorWrapper.setPowerPID();
    }
    public void setMotorPaths(MotionPath path){
        Hardware.instance.leftmotorWrapper.setPath(path);
        Hardware.instance.rightmotorWrapper.setPath(path);
    }
    public void resetMotors(){
        Hardware.instance.leftmotorWrapper.reset();
        Hardware.instance.rightmotorWrapper.reset();
        Hardware.instance.centermotorWrapper.reset();
    }
    public boolean checkMotorsFinished(){
        return Hardware.instance.leftmotorWrapper.completedDistance() && Hardware.instance.rightmotorWrapper.completedDistance();
    }
    public void strafeMotorSet(double power){
        Hardware.instance.centermotor.setPower(power);
    }
}
