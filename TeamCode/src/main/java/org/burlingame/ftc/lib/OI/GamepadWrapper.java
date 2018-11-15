package org.burlingame.ftc.lib.OI;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Wesley on 9/15/16.
 */
public class GamepadWrapper {

    private Gamepad gamepad;

    public static double TRIGGER_THRESHOLD = 0.6;

    public Stick left_stick, right_stick;
    public DoubleSupplier left_trigger_value, right_trigger_value;
    public Button left_trigger, right_trigger;

    public Button a_button, b_button, x_button, y_button;
    public Button dp_up, dp_down, dp_right, dp_left;
    public Button left_bumper, right_bumper;

    public GamepadWrapper(final Gamepad gamepad) {
        this.gamepad = gamepad;

        this.left_stick = new Stick(new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return gamepad.left_stick_x;
            }
        }, new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return gamepad.left_stick_y;
            }
        });
        this.right_stick = new Stick(new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return gamepad.right_stick_x;
            }
        }, new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return gamepad.right_stick_y;
            }
        });

        this.left_trigger_value = new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return gamepad.left_trigger;
            }
        };
        this.right_trigger_value = new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return GamepadWrapper.this.gamepad.right_trigger;
            }
        };

        this.left_trigger = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.left_trigger > TRIGGER_THRESHOLD;
            }
        });
        this.right_trigger = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.right_trigger > TRIGGER_THRESHOLD;
            }
        });
        this.a_button = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.a;
            }
        });
        this.b_button = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.b;
            }
        });
        this.x_button = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.x;
            }
        });
        this.y_button = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.y;
            }
        });
        this.right_bumper = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.right_bumper;
            }
        });
        this.left_bumper = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.left_bumper;
            }
        });
        this.dp_up = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.dpad_up;
            }
        });
        this.dp_down = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.dpad_down;
            }
        });
        this.dp_left = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.dpad_left;
            }
        });
        this.dp_right = new Button(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return gamepad.dpad_right;
            }
        });

    }
}
