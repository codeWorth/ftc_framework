package org.burlingame.ftc.lib;

import org.burlingame.ftc.lib.commands.Command;
import org.burlingame.ftc.lib.subsystem.Subsystem;

import java.util.ArrayList;
import java.util.LinkedList;

public class Scheduler {

    private static Scheduler instance;
    public static Scheduler getInstance() {
        if (instance == null) {
            instance = new Scheduler();
        }

        return instance;
    }

    public boolean inTeleop = false;
    private boolean addingFromBuffer = false;

    private ArrayList<Command> newCommands = new ArrayList<>(30);
    private LinkedList<Command> commands = new LinkedList<>();

    private ArrayList<Subsystem> subsystems = new ArrayList<>(10);
    public void registerSubsystem(Subsystem subsystem) {
        subsystems.add(subsystem);
    }

    public void add(Command cmd) {
        if (addingFromBuffer) {
            throw new IllegalArgumentException("Can't add a command at this time, either race condition, or someone put Scheduler.getInstance().add(command) in a constructor (put in initialize!)");
        }
        if (cmd != null) {
            newCommands.add(cmd);
        }
    }

    private void _add(Command cmd) {
        if (cmd == null || commands.contains(cmd)) {
            return;
        }

        for (Subsystem sub : cmd.requiredSubsystems) {
            commands.remove(sub.currentCommand);
            sub.currentCommand = cmd;
        }

        commands.add(cmd);
    }

    public void init() {
        for (Subsystem sub : subsystems) {
            if (sub.getDefaultCommand() != null) {
                _add(sub.getDefaultCommand());
            }
        }
    }

    public void loop() {

        int size = newCommands.size();
        addingFromBuffer = true;
        for (int i = 0; i < size; i++) {
            Command cmd = newCommands.get(i);
            _add(cmd);
        }
        newCommands.clear();
        addingFromBuffer = false;

        size = commands.size();
        for (int i = 0; i < size; i++) {
            Command cmd = commands.get(i);
            if (!cmd.run()) {
                commands.remove(i);
                for (Subsystem sub : cmd.requiredSubsystems) {
                    sub.currentCommandEnded();
                    if (sub.currentCommand() == null) {
                        if (sub.getDefaultCommand() != null) {
                            add(sub.getDefaultCommand());
                        }
                    } else {
                        add(sub.currentCommand());
                    }
                }
                i--;
            }
        }
    }

    public void endAuto() {
        wipe();
        inTeleop = true;
    }

    public void endTeleop() {
        wipe();
        inTeleop = false;
    }

    public void wipe() {
        newCommands.clear();
        for (Command cmd : commands) {
            cmd.interrupted();
        }
        commands.clear();
    }

}
