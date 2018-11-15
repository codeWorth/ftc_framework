package org.burlingame.ftc.lib.commands;

import org.burlingame.ftc.lib.subsystem.Subsystem;

import java.util.ArrayList;

public abstract class Command {

    public ArrayList<Subsystem> requiredSubsystems = new ArrayList<>();
    public boolean isInited;
    private boolean cancelled;

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

    public void cancel() {
        if (isInited) {
            cancelled = true;
        }
    }


    public abstract void end();
    public void _end() {
        end();
        isInited = false;
    }

    public abstract void interrupted();

}
