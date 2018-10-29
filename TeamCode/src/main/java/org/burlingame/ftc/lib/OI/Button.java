package org.burlingame.ftc.lib.OI;

import org.burlingame.ftc.lib.Scheduler;
import org.burlingame.ftc.lib.commands.Command;
import org.burlingame.ftc.lib.commands.CommandDelegate;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public class Button extends Command implements CommandDelegate {

    private BooleanSupplier source;
    private boolean prevState;
    private boolean inScheduler = false;

    public Button(BooleanSupplier source) {
        this.source = source;
    }

    private ArrayList<Command> whenPressedCommands = new ArrayList<>();
    private ArrayList<Command> whenReleasedCommands = new ArrayList<>();
    private ArrayList<Command> whileHeldCommands = new ArrayList<>();

    public void whenPressed(Command cmd) {
        if (!inScheduler) {
            Scheduler.getInstance().add(this);
        }
        cmd.parent = this;
        whenPressedCommands.add(cmd);
    }

    public void whileHeld(Command cmd) {
        if (!inScheduler) {
            Scheduler.getInstance().add(this);
        }
        cmd.parent = this;
        whileHeldCommands.add(cmd);
    }

    public void setWhenReleasedCommands(Command cmd) {
        if (!inScheduler) {
            Scheduler.getInstance().add(this);
        }
        cmd.parent = this;
        whenReleasedCommands.add(cmd);
    }

    @Override
    protected void execute() {
        if (!this.prevState && this.source.getAsBoolean()) { // rising edge
            for (Command cmd : whenPressedCommands) {
                Scheduler.getInstance().add(cmd);
            }
        }

        if (this.prevState && !this.source.getAsBoolean()) { // falling edge
            for (Command cmd : whenReleasedCommands) {
                Scheduler.getInstance().add(cmd);
            }
            for (Command cmd : whileHeldCommands) {
                cmd.cancel();
            }
        }

        if (this.source.getAsBoolean()) {
            for (Command cmd : whileHeldCommands) {
                Scheduler.getInstance().add(cmd);
            }
        }

        prevState = this.source.getAsBoolean();
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

    @Override
    public void commandEnded(Command cmd) {

    }

    @Override
    public boolean shouldContinue(Command cmd) {
        return whileHeldCommands.contains(cmd) && this.source.getAsBoolean();
    }
}
