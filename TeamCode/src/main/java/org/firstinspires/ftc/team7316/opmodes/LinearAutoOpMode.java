package org.firstinspires.ftc.team7316.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team7316.commands.DriveDistance;
import org.firstinspires.ftc.team7316.util.Constants;
import org.firstinspires.ftc.team7316.util.Hardware;
import org.firstinspires.ftc.team7316.util.Scheduler;
import org.firstinspires.ftc.team7316.util.commands.Command;
import org.firstinspires.ftc.team7316.util.subsystems.Subsystems;

/**
 * A linear OpMode that doesn't use the scheduler as much in order to reduce t-steps
 * Created by jerry on 1/27/18.
 */

@Autonomous(name="LinearAuto")
public abstract class LinearAutoOpMode extends LinearOpMode {

    Command cmd;
    private boolean finished = false;

    public void initialize() {
        Scheduler.inTeleop = false;
        Hardware.setHardwareMap(hardwareMap);
        Hardware.setTelemetry(telemetry);
        Subsystems.createSubsystems();

        cmd = new DriveDistance(Constants.inchesToTicks(10));
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        cmd.init();

        while(opModeIsActive()) {

            if (!finished) {
                cmd.loop();

                finished = cmd.shouldRemove();
            }

        }
    }
}
