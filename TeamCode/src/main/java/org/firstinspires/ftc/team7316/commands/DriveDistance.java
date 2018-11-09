package org.firstinspires.ftc.team7316.commands;

import android.util.Log;

import com.opencsv.CSVWriter;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.copypastaLib.CombinedPath;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DriveDistance extends Command {
    int ticks;
    long lastTime;
    ElapsedTime t = new ElapsedTime();
    public DriveDistance(int ticks){
        this.ticks=ticks;
    }
    @Override
    public void init() {
        t.reset();
        Subsystems.instance.driveSubsystem.resetMotors();
        if(ticks>0){
            Subsystems.instance.driveSubsystem.setMotorPaths(new CombinedPath.LongitudalTrapezoid(0,ticks,1200,1500));
        }
        else {
            Subsystems.instance.driveSubsystem.setMotorPaths((new CombinedPath.LongitudalTrapezoid(0,ticks,-1200,-1500)));
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
        return Subsystems.instance.driveSubsystem.checkMotorsFinished() || t.seconds() > 5;
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
