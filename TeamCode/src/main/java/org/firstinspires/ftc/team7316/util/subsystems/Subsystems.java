package org.firstinspires.ftc.team7316.util.subsystems;

import org.firstinspires.ftc.team7316.util.Scheduler;

/**
 * Every subsystem that needs to be used is placed in here.
 */

public class Subsystems {

    public static Subsystems instance = null;

    public Subsystem[] subsystems;
    public DriveSubsystem driveSubsystem;
    public ClimberSubsystem climberSubsystem;
    public PlateSubsystem plateSubsystem;
    public IntakeSubsystem intakeSubsystem;

    private Subsystems () {
        plateSubsystem=new PlateSubsystem();
        climberSubsystem=new ClimberSubsystem();
        driveSubsystem = new DriveSubsystem();
        intakeSubsystem = new IntakeSubsystem();
        subsystems = new Subsystem[]{driveSubsystem,climberSubsystem,plateSubsystem,intakeSubsystem};
    }

    public static void createSubsystems() {
        instance = new Subsystems();

        Scheduler.instance.addDefaultCommands();

        instance.resetSubsystems();
    }

    public void resetSubsystems() {
        for(Subsystem s : subsystems) {
            s.reset();
        }
    }
}