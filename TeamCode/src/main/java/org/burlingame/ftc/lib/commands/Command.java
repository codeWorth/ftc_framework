package org.burlingame.ftc.lib.commands;

import org.burlingame.ftc.lib.subsystem.Subsystem;

import java.util.ArrayList;

public abstract class Command {

    public ArrayList<Subsystem> requiredSubsystems = new ArrayList<>();
    public boolean isInited;
    private boolean cancelled;
    public CommandDelegate parent;

    protected abstract void execute();
    public boolean run() {
        if (!isInited) {
            _init();
        }

        execute();
        return !_isFinished();
    }

    public abstract void init();
    public void _init() {
        init();
        cancelled = false;
        isInited = true;
    }

    protected abstract boolean isFinished();
    public boolean _isFinished() {
        if (cancelled) {
            return true;
        } else {
            return isFinished();
        }
    }

    public boolean canResume() {
        for (Subsystem sub : this.requiredSubsystems) {
            if (sub.currentCommand() != this) {
                return false;
            }
        }
        return true;
    }

    public void cancel() {
        if (isInited) {
            cancelled = true;
        }
    }

    /**
     * Pause or end this command, depending on what the parent says. Similar to interrupted
     * @return True if this command is done now, false if it might continue later
     */
    public boolean pause() {
        if (this.parent != null && this.parent.shouldContinue(this)) {
            interrupted();
            return false;
        } else {
            interrupted();
            isInited = false;
            return true;
        }
    }

    public abstract void end();
    public void _end() {
        end();
        if (this.parent != null) {
            this.parent.commandEnded(this);
        }
        isInited = false;
    }

    public abstract void interrupted();

}
