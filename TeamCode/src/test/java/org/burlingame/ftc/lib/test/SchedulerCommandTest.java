package org.burlingame.ftc.lib.test;

import android.util.Log;

import junit.framework.Assert;

import org.burlingame.ftc.lib.commands.Command;
import org.burlingame.ftc.lib.commands.Scheduler;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class SchedulerCommandTest {

    private static Scheduler sched = Scheduler.getInstance();

    @Before
    public void setUpScheduler() {
        sched.reset();
        sched.inTeleop = false;
    }

    @Test
    public void testSingleCommand() {
        DebugCommand c = new DebugCommand();

        sched.init();

        sched.loop();
        c.testAssert(0, 0, 0, 0, 0);
        assertFalse(c.isRunning());

        sched.add(c);
        
        sched.loop();
        assertTrue(c.isRunning());
        c.testAssert(1, 1, 1, 0, 0);

        sched.loop();
        c.testAssert(1, 2, 2, 0, 0);

        c.isFinished = true;
        sched.loop();
        c.testAssert(1, 3, 3, 1, 0);
        assertFalse(c.isRunning());

        sched.loop();
        c.testAssert(1, 3, 3, 1, 0);
        assertFalse(c.isRunning());
    }

    @Test
    public void testMultipleCommands() {
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        DebugCommand c3 = new DebugCommand();

        sched.init();

        sched.add(c1);
        sched.loop();
        c1.testAssert(1, 1, 1, 0, 0);

        sched.add(c2);
        sched.add(c3);
        sched.loop();
        c1.testAssert(1, 2, 2, 0, 0);
        c2.testAssert(1, 1, 1, 0, 0);
        c3.testAssert(1, 1, 1, 0, 0);

        sched.loop();
        c1.testAssert(1, 3, 3, 0, 0);
        c2.testAssert(1, 2, 2, 0, 0);
        c3.testAssert(1, 2, 2, 0, 0);

        c2.isFinished = true;
        sched.loop();
        c1.testAssert(1, 4, 4, 0, 0);
        c2.testAssert(1, 3, 3, 1, 0);
        c3.testAssert(1, 3, 3, 0, 0);

        sched.loop();
        c1.testAssert(1, 5, 5, 0, 0);
        c2.testAssert(1, 3, 3, 1, 0);
        c3.testAssert(1, 4, 4, 0, 0);

        c1.isFinished = true;
        c3.isFinished = true;
        sched.loop();
        c1.testAssert(1, 6, 6, 1, 0);
        c2.testAssert(1, 3, 3, 1, 0);
        c3.testAssert(1, 5, 5, 1, 0);

        sched.loop();
        c1.testAssert(1, 6, 6, 1, 0);
        c2.testAssert(1, 3, 3, 1, 0);
        c3.testAssert(1, 5, 5, 1, 0);
    }

    @Test
    public void testParallelCommands() {
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        DebugCommand c3 = new DebugCommand();

        Command c = c1.and(c2).and(c3);

        sched.init();

        sched.loop();
        assertFalse(c.isRunning());
        c1.testAssert(0, 0, 0, 0, 0);
        c2.testAssert(0, 0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0, 0);

        sched.add(c);

        sched.loop();
        assertTrue(c.isRunning());
        c1.testAssert(1, 1, 0, 0);
        c2.testAssert(1, 1, 0, 0);
        c3.testAssert(1, 1, 0, 0);

        sched.loop();
        c1.testAssert(1, 2, 0, 0);
        c2.testAssert(1, 2, 0, 0);
        c3.testAssert(1, 2, 0, 0);

        c2.isFinished = true;
        sched.loop();
        c1.testAssert(1, 3, 0, 0);
        c2.testAssert(1, 3, 1, 0);
        c3.testAssert(1, 3, 0, 0);

        c3.isFinished = true;
        sched.loop();
        c1.testAssert(1, 4, 0, 0);
        c2.testAssert(1, 3, 1, 0);
        c3.testAssert(1, 4, 1, 0);

        c1.isFinished = true;
        sched.loop();
        c1.testAssert(1, 5, 1, 0);
        c2.testAssert(1, 3, 1, 0);
        c3.testAssert(1, 4, 1, 0);

        sched.loop();
        assertFalse(c.isRunning());
    }

    @Test
    public void testSequentialCommands() {
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        DebugCommand c3 = new DebugCommand();

        Command c = c1.then(c2).then(c3);

        sched.init();

        sched.loop();
        assertFalse(c.isRunning());
        c1.testAssert(0, 0, 0, 0, 0);
        c2.testAssert(0, 0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0, 0);

        sched.add(c);
        sched.loop();
        assertTrue(c.isRunning());
        c1.testAssert(1, 1, 0, 0);
        c2.testAssert(0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0);

        sched.loop();
        c1.testAssert(1, 2, 0, 0);
        c2.testAssert(0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0);

        c1.isFinished = true;
        sched.loop();
        assertTrue(c.isRunning());
        c1.testAssert(1, 3, 1, 0);
        c2.testAssert(0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0);

        sched.loop();
        c1.testAssert(1, 3, 1, 0);
        c2.testAssert(1, 1, 0, 0);
        c3.testAssert(0, 0, 0, 0);

        c2.isFinished = true;
        c3.isFinished = true;
        sched.loop();
        assertTrue(c.isRunning());
        c1.testAssert(1, 3, 1, 0);
        c2.testAssert(1, 2, 1, 0);
        c3.testAssert(0, 0, 0, 0);

        sched.loop();
        assertFalse(c.isRunning());
        c1.testAssert(1, 3, 1, 0);
        c2.testAssert(1, 2, 1, 0);
        c3.testAssert(1, 1, 1, 0);

    }

    @Test
    public void testSubsystemAutoDefault() {
        sched.inTeleop = false;
        DebugSubsystem s = new DebugSubsystem();
        DebugCommand auto = new DebugCommand();
        DebugCommand tele = new DebugCommand();

        auto.require(s);
        tele.require(s);

        s.defaultAuto = auto;
        s.defaultTeleop = tele;
        sched.registerSubsystem(s);

        sched.init();

        sched.loop();
        sched.loop();
        Assert.assertTrue(auto.isRunning());
        Assert.assertFalse(tele.isRunning());
    }

    @Test
    public void testSubsystemTeleDefault() {
        sched.inTeleop = true;
        DebugSubsystem s = new DebugSubsystem();
        DebugCommand auto = new DebugCommand();
        DebugCommand tele = new DebugCommand();

        auto.require(s);
        tele.require(s);

        s.defaultAuto = auto;
        s.defaultTeleop = tele;
        sched.registerSubsystem(s);

        sched.init();

        sched.loop();
        sched.loop();
        Assert.assertFalse(auto.isRunning());
        Assert.assertTrue(tele.isRunning());
    }

    @Test
    public void shouldInterruptDefaultCommandWhenNewCommandAdded() {
        sched.inTeleop = false;
        DebugSubsystem s = new DebugSubsystem();
        DebugCommand def = new DebugCommand();
        DebugCommand add = new DebugCommand();
        def.require(s);
        add.require(s);

        s.defaultAuto = def;

        sched.init();
        sched.loop();
        def.testAssert(1, 1, 0, 0);
        add.testAssert(0, 0, 0, 0);

        sched.add(add);
        sched.loop();
        def.testAssert(1, 1, 1, 0, 1);
        add.testAssert(1, 1, 1, 0, 0);

        sched.loop();
        def.testAssert(1, 1,0, 1);
        add.testAssert(1, 2,0, 0);

        add.isFinished = true;
        sched.loop();
        def.testAssert(1, 1,0, 1);
        add.testAssert(1, 3,1, 0);

        sched.loop();
        def.testAssert(2, 2,0, 1);
        add.testAssert(1, 3,1, 0);

    }
}
