package org.firstinspires.ftc.team7316.util;

/**
 * Created by wayne on 10/21/2016.
 */
public class Constants {

    public static final double SQRT2 = Math.sqrt(2);
    public static final double RADIAN_TO_DEGREES = 180 / Math.PI;
    public static final double DEGREES_TO_RADIANS = Math.PI / 180;

    // These rudimentary variables describe the robot for important auto calculations
    // Change them as you please
    public static final double JOYSTICK_DRIVE_DEADZONE = 0.05;
    public static final double DRIVER_MOTOR_DEADZONE = 0.1;
    public static final double ENCODER_TICK_PER_REV = 1120;
    public static final double ENCODER_REV_PER_WHEEL_REV = 0.5;
    public static final double WHEEL_RADIUS = 2; // inches
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_RADIUS * 2 * Math.PI;
    public static final double SLOW_SPEED = 0.6;

    public static final double AUTO_DEADZONE = 0.15;
    public static final int DISTANCE_ERROR_RANGE_TICKS = 10;
    public static final double ACCELERATION_SPEED=0.2;

    public static final double DRIVE_P = 0;
    public static final double DRIVE_I = 0;
    public static final double DRIVE_D = 0;
    public static final double DRIVE_F = 0;

    /**
     * @param dist inches
     */
    public static int inchesToTicks(double dist) {
        return (int)(ENCODER_TICK_PER_REV * ENCODER_REV_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE * dist);
    }

    /**
     * @param dist millimeters
     */
    public static int millimetersToTicks(double dist) {
        return (int)(ENCODER_TICK_PER_REV * ENCODER_REV_PER_WHEEL_REV / (WHEEL_CIRCUMFERENCE * 25.4) * dist);
    }
}
