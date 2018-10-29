package org.firstinspires.ftc.team7316.util.modes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.Scheduler;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

/**
 * A linear OpMode that doesn't use the scheduler as much in order to reduce t-steps
 * Created by jerry on 1/27/18.
 */

public abstract class LinearAutoOpMode extends LinearOpMode {

    private boolean finished = false;

    /**
     * Define a command sequence in here to run in the opmode
     * @return the auto command sequence
     */
    public abstract Command[] createCommands();

    public void initialize() {
        Scheduler.inTeleop = false;
        Scheduler.instance.clear();
        Hardware.setHardwareMap(hardwareMap);
        Hardware.setTelemetry(telemetry);
        Subsystems.createSubsystems();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        Command[] cmds = createCommands();

        waitForStart();

        while(opModeIsActive()) {
            if(!finished) {
                Command.run(cmds, this);
                finished = true;
            }
        }
    }
}
