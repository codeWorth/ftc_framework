package org.burlingame.ftc.lib.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.burlingame.ftc.lib.Hardware;
import org.burlingame.ftc.lib.commands.Scheduler;
import org.burlingame.ftc.lib.subsystem.Subsystems;

/**
 * Opmode that does all the stuff needed for auto only
 */

public abstract class AutoBaseOpMode extends OpMode {

    /**
     * The timeout allowed is only this long since Vuforia took a long time to initialize.
     * Remove the constructor body if you want.
     */
    public AutoBaseOpMode() {
//        msStuckDetectInit = 10000;
    }

    @Override
    public void init() {
        Log.i(Hardware.tag, "=========================== STARTING AUTO ================================");
        Scheduler.getInstance().inTeleop = false;
        Scheduler.getInstance().clearCommands();
        Hardware.setHardwareMap(hardwareMap);
        Hardware.setTelemetry(telemetry);
        Subsystems.createSubsystems();
        Scheduler.getInstance().init();
        onInit();
    }

    @Override
    public void loop() {
        Scheduler.getInstance().loop();
        onLoop();
    }

    @Override
    public void stop() {
        Scheduler.getInstance().clearCommands();
    }

    /**
     * Runs after Hardware is initialized, do other inits here
     */
    public abstract void onInit();

    /**
     * Runs every loop after Scheduler loops
     */
    public abstract void onLoop();
}
