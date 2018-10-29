package org.firstinspires.ftc.team7316.util;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team7316.util.motorwrappers.DCMotorWrapper;

/**
 * Contains all the hardware names and actual hardware objects
 */

public class Hardware {

    public static Hardware instance = null;

    public static final String tag = "IronPanthers";

    public static Telemetry telemetry;

    public DcMotor leftmotor;
    public DcMotor rightmotor;
    public DcMotor centermotor;
    public DcMotor climbmotor;
    public Servo plateServo;
    public BNO055IMU imu;

    public DCMotorWrapper leftmotorWrapper;
    public DCMotorWrapper rightmotorWrapper;
    public DCMotorWrapper centermotorWrapper;
    public GyroWrapper gyroWrapper;

    //Create all the hardware fields
    public final String leftMotorName = "lmotor";
    public final String rightMotorName = "rmotor";
    public final String centerMotorName = "cmotor";
    public final String climbMotorName = "clmotor";
    public final String plateServoName = "bservo";
    public final String imuname = "gyro";

    /**
     * Initialize all the hardware fields here
     */
    public Hardware (HardwareMap map) {
        leftmotor = map.dcMotor.get(leftMotorName);
        rightmotor= map.dcMotor.get(rightMotorName);
        centermotor=map.dcMotor.get(centerMotorName);
        climbmotor=map.dcMotor.get(climbMotorName);
        plateServo=map.servo.get(plateServoName);
        BNO055IMU.Parameters gyroParams = new BNO055IMU.Parameters();
        gyroParams.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        gyroParams.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        gyroParams.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        gyroParams.loggingEnabled      = true;
        gyroParams.loggingTag          = "IMU";
        gyroParams.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = map.get(BNO055IMU.class, imuname);
        imu.initialize(gyroParams);
        gyroWrapper = new GyroWrapper(imu);

        leftmotorWrapper = new DCMotorWrapper(leftmotor, new PID(Constants.DRIVE_P, Constants.DRIVE_I, Constants.DRIVE_D, Constants.DRIVE_F, 0));
        rightmotorWrapper = new DCMotorWrapper(rightmotor, new PID(Constants.DRIVE_P, Constants.DRIVE_I, Constants.DRIVE_D, Constants.DRIVE_F, 0));
        centermotorWrapper = new DCMotorWrapper(centermotor, new PID(Constants.DRIVE_P, Constants.DRIVE_I, Constants.DRIVE_D, Constants.DRIVE_F, 0));
    }

    public static void setHardwareMap(HardwareMap map) {
        instance = new Hardware(map);
    }

    public static void setTelemetry(Telemetry telemetry) {
        Hardware.telemetry = telemetry;
    }

    public static void log(String caption, Object value) {
        if (telemetry != null) {
            telemetry.addData(caption, value);
        }
    }
}