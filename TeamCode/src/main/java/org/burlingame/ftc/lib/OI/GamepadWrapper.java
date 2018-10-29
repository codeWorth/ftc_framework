package org.burlingame.ftc.lib.OI;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.DoubleSupplier;

/**
 * Created by Wesley on 9/15/16.
 */
public class GamepadWrapper {

    private Gamepad gamepad;

    public Stick left_stick, right_stick;
    public DoubleSupplier left_trigger, right_trigger;

    public Button a_button, b_button, x_button, y_button;
    public Button dp_up, dp_down, dp_right, dp_left;
    public Button left_bumper, right_bumper;

    public GamepadWrapper(Gamepad gamepad) {
        this.gamepad = gamepad;

        this.left_stick = new Stick(() -> this.gamepad.left_stick_x, () -> this.gamepad.left_stick_y);
        this.right_stick = new Stick(() -> this.gamepad.right_stick_x, () -> this.gamepad.right_stick_y);

        this.left_trigger = () -> this.gamepad.left_trigger;
        this.right_trigger = () -> this.gamepad.right_trigger;

        this.a_button = new Button(() -> this.gamepad.a);
        this.b_button = new Button(() -> this.gamepad.b);
        this.x_button = new Button(() -> this.gamepad.x);
        this.y_button = new Button(() -> this.gamepad.y);
        this.dp_up = new Button(() -> this.gamepad.dpad_up);
        this.dp_down = new Button(() -> this.gamepad.dpad_down);
        this.dp_right = new Button(() -> this.gamepad.dpad_right);
        this.dp_left = new Button(() -> this.gamepad.dpad_left);
        this.left_bumper = new Button(() -> this.gamepad.left_bumper);
        this.right_bumper = new Button(() -> this.gamepad.right_bumper);

    }
}
