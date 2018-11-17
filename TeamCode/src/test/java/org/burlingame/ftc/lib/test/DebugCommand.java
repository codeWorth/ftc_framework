package org.burlingame.ftc.lib.test;

import junit.framework.Assert;

import org.burlingame.ftc.lib.commands.Command;

public class DebugCommand extends Command {

    public int initCalled = 0;
    public int executeCalled = 0;
    public int isFinishedCalled = 0;
    public int endCalled = 0;
    public int interruptedCalled = 0;
    public boolean isFinished = false;

    public void testAssert(int init, int exec, int fin, int end, int inter) {
        Assert.assertEquals(init, initCalled);
        Assert.assertEquals(exec, executeCalled);
        Assert.assertEquals(fin, isFinishedCalled);
        Assert.assertEquals(end, endCalled);
        Assert.assertEquals(inter, interruptedCalled);
    }

    public void clear() {
        initCalled = 0;
        executeCalled = 0;
        isFinishedCalled = 0;
        endCalled = 0;
        interruptedCalled = 0;
        isFinished = false;
    }

    @Override
    protected void init() {
        initCalled++;
    }

    @Override
    protected void execute() {
        executeCalled++;
    }

    @Override
    protected boolean isFinished() {
        isFinishedCalled++;
        return isFinished;
    }

    @Override
    protected void end() {
        endCalled++;
    }

    @Override
    protected void interrupted() {
        interruptedCalled++;
    }
}
