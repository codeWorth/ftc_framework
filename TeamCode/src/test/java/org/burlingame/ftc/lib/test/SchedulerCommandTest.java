package org.burlingame.ftc.lib.test;

import org.burlingame.ftc.lib.commands.Command;
import org.burlingame.ftc.lib.commands.Scheduler;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class SchedulerCommandTest {

    @Before
    public void setUpScheduler() {
        Scheduler.getInstance().clearCommands();
    }

    @Test
    public void testSingleCommand() {
        Scheduler sched = Scheduler.getInstance();
        DebugCommand c = new DebugCommand();

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
        Scheduler sched = Scheduler.getInstance();
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        DebugCommand c3 = new DebugCommand();

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
        Scheduler sched = Scheduler.getInstance();
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        DebugCommand c3 = new DebugCommand();

        Command c = c1.whilst(c2).whilst(c3);

        sched.loop();
        assertFalse(c.isRunning());
        c1.testAssert(0, 0, 0, 0, 0);
        c2.testAssert(0, 0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0, 0);

        sched.add(c);
        sched.loop();
        assertTrue(c.isRunning());
        c1.testAssert(0, 0, 0, 0, 0);
        c2.testAssert(0, 0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0, 0);

        sched.loop();
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
        Scheduler sched = Scheduler.getInstance();
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        DebugCommand c3 = new DebugCommand();

        Command c = c1.then(c2).then(c3);

        sched.loop();
        assertFalse(c.isRunning());
        c1.testAssert(0, 0, 0, 0, 0);
        c2.testAssert(0, 0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0, 0);

        sched.add(c);
        sched.loop();
        assertTrue(c.isRunning());
        c1.testAssert(0, 0, 0, 0, 0);
        c2.testAssert(0, 0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0, 0);

        sched.loop();
        c1.testAssert(1, 1, 0, 0);
        c2.testAssert(0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0);

        sched.loop();
        c1.testAssert(2, 2, 0, 0);
        c2.testAssert(0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0);

        c1.isFinished = true;
        sched.loop();
        c1.testAssert(3, 3, 1, 0);
        c2.testAssert(0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0);

        sched.loop();
        assertFalse(c.isRunning());
    }

}
