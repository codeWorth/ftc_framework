package org.firstinspires.ftc.team7316.util.subsystems;

import org.firstinspires.ftc.team7316.commands.TeleopTray;
import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;

public class TraySubsystem extends Subsystem {
    @Override
    public void reset() {
        Hardware.instance.trayAngleServo.setPosition(Constants.TRAY_ANGLE_SERVO_RETRACTED);
        Hardware.instance.trayExtendServoLeft.setPosition(Constants.TRAY_SERVO_RETRACTED);
        Hardware.instance.trayExtendServoRight.setPosition(Constants.TRAY_SERVO_RETRACTED);
    }

    @Override
    public Command defaultAutoCommand() {
        return null;
    }

    @Override
    public Command defaultTeleopCommand() {
        return new TeleopTray();
    }
    public void extendTray(){
        Hardware.instance.trayExtendServoLeft.setPosition(Constants.TRAY_SERVO_EXTENDED);
        Hardware.instance.trayExtendServoRight.setPosition(Constants.TRAY_SERVO_EXTENDED);
    }
    public void retractTray(){
        Hardware.instance.trayExtendServoRight.setPosition(Constants.TRAY_SERVO_RETRACTED);
        Hardware.instance.trayExtendServoLeft.setPosition(Constants.TRAY_SERVO_RETRACTED);
    }
    public void setTrayAngle(double position){
        Hardware.instance.trayAngleServo.setPosition(position);
    }
}
