package org.burlingame.ftc.lib.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.burlingame.ftc.lib.Hardware;
import org.burlingame.ftc.lib.Scheduler;
import org.burlingame.ftc.lib.subsystem.Subsystems;

/**
 * OpMode that initializes Hardware and Scheduler for you. Please use this instead of rewriting the
 * same things over and over again because it's much nicer.
 */
public abstract class TeleopBaseOpMode extends OpMode {
    @Override
    public void init() {
        Log.i(Hardware.tag, "======================================= STARTING TELEOP ===================================================");
        Scheduler.getInstance().inTeleop = true;
        Scheduler.getInstance().wipe();
        Hardware.setHardwareMap(hardwareMap);
        Hardware.setTelemetry(telemetry);
        OI.createInputs(gamepad1, gamepad2);
        Subsystems.createSubsystems();
        Scheduler.getInstance().init();
        onInit();
    }


    @Override
    public void loop() {
        Scheduler.instance.loop();
        onLoop();
    }

    @Override
    public void stop() {
        Subsystems.instance.resetSubsystems();
    }

    /**
     * Runs after Hardware is initialized
     */
    public abstract void onInit();

    /**
     * Runs every loop after Scheduler loops
     */
    public abstract void onLoop();

}
