package org.burlingame.ftc.lib.OI;

import org.burlingame.ftc.lib.Scheduler;
import org.burlingame.ftc.lib.commands.Command;

import java.util.ArrayList;

public class Button extends Command {

    private BooleanWrapper source;
    private boolean prevState;

    public Button(BooleanWrapper source) {
        this.source = source;
    }

    private ArrayList<Command> whenPressedCommands = new ArrayList<>();
    private ArrayList<Command> whenReleasedCommands = new ArrayList<>();
    private ArrayList<Command> whileHeldCommands = new ArrayList<>();

    public void whenPressed(Command cmd) {
        whenPressedCommands.add(cmd);
    }


    @Override
    protected void execute() {
        if (!this.prevState && this.source.state()) { // rising edge
            for (Command cmd : whenPressedCommands) {
                Scheduler.getInstance().add(cmd);
            }
        }

        if (this.prevState && !this.source.state()) { // falling edge
            for (Command cmd : whenReleasedCommands) {
                Scheduler.getInstance().add(cmd);
            }
            for (Command cmd : whileHeldCommands) {
                cmd.cancel();
            }
        }

        if (this.source.state()) {
            for (Command cmd : whileHeldCommands) {
                Scheduler.getInstance().add(cmd);
            }
        }
    }

    @Override
    public void init() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end() {

    }

    @Override
    public void interrupted() {

    }
}
