package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class LowerPlateServo extends Command {
    @Override
    public void init() {
        Subsystems.instance.plateSubsystem.servoLower();
    }

    @Override
    public void loop() {

    }

    @Override
    public boolean shouldRemove() {
        return true;
    }

    @Override
    protected void end() {

    }
}
