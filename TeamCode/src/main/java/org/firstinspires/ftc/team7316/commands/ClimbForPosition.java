package org.firstinspires.ftc.team7316.commands;

import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystem;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

public class ClimbForPosition extends Command {

    double movePower = -1;
    long ticksGoal;
    double direction = 1;

    public ClimbForPosition(long ticksGoal) {
//        requires(Subsystems.instance.climberSubsystem);
        this.ticksGoal = ticksGoal;
    }

    @Override
    public void init() {
        if (ticksGoal < Subsystems.instance.climberSubsystem.currentPosition()) {
            direction = -1;
        }
    }

    @Override
    public void loop() {
        Subsystems.instance.climberSubsystem.setMotor(direction * movePower);
    }

    @Override
    public boolean shouldRemove() {
        if (direction < 0) {
            return Subsystems.instance.climberSubsystem.currentPosition() < ticksGoal;
        } else {
            return Subsystems.instance.climberSubsystem.currentPosition() > ticksGoal;
        }
    }

    @Override
    protected void end() {
        Subsystems.instance.climberSubsystem.setMotor(0);
    }
}
