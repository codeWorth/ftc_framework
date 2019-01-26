package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.input.OI;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class TeleopTray extends Command {
    @Override
    public void init() {
        requires(Subsystems.instance.traySubsystem);
        Subsystems.instance.traySubsystem.reset();
    }

    @Override
    public void loop() {
        if(Math.abs(OI.instance.gp2.right_stick.getY())> Math.abs(Constants.TRAY_DEADZONE)){
            Subsystems.instance.traySubsystem.extendTray();
            Subsystems.instance.traySubsystem.setTrayAngle(OI.instance.gp2.right_stick.getY());
        }
        else {
            Subsystems.instance.traySubsystem.reset();
        }
    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    protected void end() {

    }
}
