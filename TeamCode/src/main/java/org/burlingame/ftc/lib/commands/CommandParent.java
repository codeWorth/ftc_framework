package org.burlingame.ftc.lib.commands;

public interface CommandParent {

    void onCommandFinish(Command command, boolean interrupted);

}
