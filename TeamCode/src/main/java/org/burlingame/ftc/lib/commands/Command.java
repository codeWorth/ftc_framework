package org.burlingame.ftc.lib.commands;

import org.burlingame.ftc.lib.subsystem.Subsystem;

import java.util.ArrayList;

public abstract class Command {

    public ArrayList<Subsystem> requiredSubsystems = new ArrayList<>();
    public boolean isInited;
    private boolean cancelled;

    Command next = null;
    Command prev = null;

    public void remove() {
        prev.next = next;
        next.prev = prev;
    }

    protected abstract void execute();
    public boolean run() {
        if (!isInited) {
            _init();
        }

        execute();
        return _isFinished();
    }

    protected abstract void init();
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


    protected abstract void end();
    public void _end() {
        end();
        isInited = false;
    }

    protected abstract void interrupted();
    public void _interrupted() {
        interrupted();
        isInited = false;
    }

}
