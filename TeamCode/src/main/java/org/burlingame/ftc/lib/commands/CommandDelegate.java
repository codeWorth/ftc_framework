package org.burlingame.ftc.lib.commands;

public interface CommandDelegate {

    void commandEnded(Command cmd);
    boolean shouldContinue(Command cmd);

}
