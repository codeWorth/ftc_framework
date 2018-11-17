package org.burlingame.ftc.lib.commands;

import org.burlingame.ftc.lib.subsystem.Subsystem;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class Command {

    public Set<Subsystem> requiredSubsystems = new HashSet<>();
    private boolean isInited;
    private boolean running = false;
    private boolean cancelled;

    CommandParent parent;

    // Linked list
    Command next = null;
    Command prev = null;

    public Command whilst(Command other) {
        return new ParallelGroup(this, other);
    }

    public Command then(Command other) {
        return new SequenceGroup(this, other);
    }

    protected void require(Subsystem subsystem) {
        requiredSubsystems.add(subsystem);
    }

    protected void require(Collection<Subsystem> subsystems) {
        requiredSubsystems.addAll(subsystems);
    }

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
        running = true;
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
        running = false;
        if (parent != null) {
            parent.onCommandFinish(this, false);
        }
    }

    protected abstract void interrupted();
    public void _interrupted() {
        interrupted();
        isInited = false;
        running = false;
        if (parent != null) {
            parent.onCommandFinish(this, true);
        }
    }

    public boolean isRunning() {
        return running;
    }

}
