package org.firstinspires.ftc.team7316.commands;

import android.util.Log;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.copypastaLib.CombinedPath;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

import java.util.ArrayList;

public class DriveDistance extends Command {
    int ticks;
    long lastTime;
    double pathTime = 0;
    ElapsedTime t = new ElapsedTime();
    public DriveDistance(int ticks){
        this.ticks=ticks;
    }
    @Override
    public void init() {
        t.reset();
        Subsystems.instance.driveSubsystem.resetMotors();
        CombinedPath.LongitudalTrapezoid pth;
        if(ticks>0){
            pth = new CombinedPath.LongitudalTrapezoid(0,ticks,Constants.MAX_TICKS_SPEED,Constants.MAX_TICKS_ACCEL);
        }
        else {
            pth = new CombinedPath.LongitudalTrapezoid(0,ticks,-Constants.MAX_TICKS_SPEED,-Constants.MAX_TICKS_ACCEL);
        }

        pathTime = pth.getTotalTime();
        Subsystems.instance.driveSubsystem.setMotorPaths(pth);
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
        return Subsystems.instance.driveSubsystem.checkMotorsFinished() || t.seconds() > pathTime + 1;
    }

    @Override
    protected void end() {

        writeData("left", Hardware.instance.leftmotorWrapper.pid.dataPoints);
        writeData("right", Hardware.instance.rightmotorWrapper.pid.dataPoints);

        for (int i = 0;i < 6; i++) {
            Log.d("---PATH---", "-----------WRITING PATH DATA---------");
        }

        Subsystems.instance.driveSubsystem.driveMotorSet(0,0);
    }

    private void writeData(String motorName, ArrayList<String[]> dataList) {
        Log.d(motorName+"_AutoInfo","Info:\n" +
                "\tPIDF Constants: " + String.format("%s,%s,%s\n", Constants.DRIVE_P, Constants.DRIVE_I, Constants.DRIVE_D) +
                "\tData Order: Time, Error, Predicted Speed, Actual Speed, Predicted Position, Actual Position Power \n");

        for (String[] data : dataList) {
            String line = "";
            for (String str : data) {
                line += str + ",";
            }
            Log.d(motorName+"_AutoData", line);
        }
    }
}
