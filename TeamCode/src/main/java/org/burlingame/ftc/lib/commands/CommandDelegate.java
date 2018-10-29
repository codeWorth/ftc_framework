package org.burlingame.ftc.lib.commands;

public interface CommandDelegate {

    void commandEnded();
    boolean shouldContinue();

}
