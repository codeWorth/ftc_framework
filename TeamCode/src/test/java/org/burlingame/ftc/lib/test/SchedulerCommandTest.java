package org.burlingame.ftc.lib.test;

import org.burlingame.ftc.lib.commands.Command;
import org.burlingame.ftc.lib.commands.Scheduler;
import org.burlingame.ftc.lib.subsystem.Subsystem;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.burlingame.ftc.lib.commands.Parallel.all;
import static org.burlingame.ftc.lib.commands.Parallel.any;

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
    public void testParallelAll() {
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        DebugCommand c3 = new DebugCommand();

        Command c = all(c1, c2, c3);

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
    public void testParallelAny() {
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        DebugCommand c3 = new DebugCommand();

        Command par = any(c1, c2, c3);

        sched.init();

        sched.loop();
        assertFalse(par.isRunning());
        c1.testAssert(0, 0, 0, 0, 0);
        c2.testAssert(0, 0, 0, 0, 0);
        c3.testAssert(0, 0, 0, 0, 0);

        sched.add(par);

        sched.loop();
        assertTrue(par.isRunning());
        c1.testAssert(1, 1, 0, 0);
        c2.testAssert(1, 1, 0, 0);
        c3.testAssert(1, 1, 0, 0);

        sched.loop();
        c1.testAssert(1, 2, 0, 0);
        c2.testAssert(1, 2, 0, 0);
        c3.testAssert(1, 2, 0, 0);

        c2.isFinished = true;
        sched.loop();
        c1.testAssert(1, 3, 0, 1);
        c2.testAssert(1, 3, 1, 0);
        c3.testAssert(1, 3, 0, 1);

        sched.loop();
        assertFalse(par.isRunning());
        c1.testAssert(1, 3, 0, 1);
        c2.testAssert(1, 3, 1, 0);
        c3.testAssert(1, 3, 0, 1);
    }

    @Test
    public void testSequential() {
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
        assertTrue(auto.isRunning());
        assertFalse(tele.isRunning());
    }

    @Test
    public void testSubsystemTeleopDefault() {
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
        assertFalse(auto.isRunning());
        assertTrue(tele.isRunning());
    }

    @Test
    public void shouldInterruptDefaultCommandWhenAddedNewCommand() {
        sched.inTeleop = false;
        DebugSubsystem s = new DebugSubsystem();
        DebugCommand def = new DebugCommand();
        DebugCommand add = new DebugCommand();
        def.require(s);
        add.require(s);

        s.defaultAuto = def;

        sched.registerSubsystem(s);

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

    @Test
    public void shouldInterruptPreviousCommandWhenAddedNewCommand() {
        DebugSubsystem s1 = new DebugSubsystem();
        DebugSubsystem s2 = new DebugSubsystem();
        DebugSubsystem s3 = new DebugSubsystem();
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        c1.require(s1);
        c1.require(s2);
        c2.require(s2);
        c2.require(s3);

        sched.registerSubsystem(s1);
        sched.registerSubsystem(s2);
        sched.registerSubsystem(s3);

        sched.add(c1);
        sched.loop();
        assertEquals(c1, s1.currentCommand);
        assertEquals(c1, s2.currentCommand);
        assertNull(s3.currentCommand);
        c1.testAssert(1, 1, 0, 0);
        c2.testAssert(0, 0, 0, 0);

        sched.add(c2);
        sched.loop();
        assertNull(s1.currentCommand);
        assertEquals(c2, s2.currentCommand);
        assertEquals(c2, s3.currentCommand);
        c1.testAssert(1, 1, 0, 1);
        c2.testAssert(1, 1, 0, 0);
    }

    @Test
    public void shouldNotInterruptWhenAddedDisjointRequires() {
        DebugSubsystem s1 = new DebugSubsystem();
        DebugSubsystem s2 = new DebugSubsystem();
        DebugSubsystem s3 = new DebugSubsystem();
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        c1.require(s1);
        c2.require(s2);
        c2.require(s3);
        sched.registerSubsystem(s1);
        sched.registerSubsystem(s2);
        sched.registerSubsystem(s3);

        sched.add(c1);
        sched.loop();
        assertEquals(c1, s1.currentCommand);
        assertNull(s2.currentCommand);
        assertNull(s3.currentCommand);
        c1.testAssert(1, 1, 0, 0);
        c2.testAssert(0, 0, 0, 0);

        sched.add(c2);
        sched.loop();
        assertEquals(c1, s1.currentCommand);
        assertEquals(c2, s2.currentCommand);
        assertEquals(c2, s3.currentCommand);
        c1.testAssert(1, 2, 0, 0);
        c2.testAssert(1, 1, 0, 0);
    }

    @Test
    public void shouldInterruptParallelAllWhenAddedNewCommand() {
        DebugSubsystem s = new DebugSubsystem();
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        DebugCommand c3 = new DebugCommand();
        c1.require(s);
        c3.require(s);
        Command par = all(c1, c2);
        sched.registerSubsystem(s);

        sched.add(par);
        sched.loop();
        assertEquals(par, s.currentCommand);
        assertTrue(par.isRunning());
        assertTrue(c1.isRunning());
        assertTrue(c2.isRunning());
        assertFalse(c3.isRunning());

        sched.add(c3);
        sched.loop();
        assertEquals(c3, s.currentCommand);
        assertTrue(c1.interruptedCalled > 0);
        assertTrue(c2.interruptedCalled > 0);
        assertFalse(par.isRunning());
        assertFalse(c1.isRunning());
        assertFalse(c2.isRunning());
        assertTrue(c3.isRunning());
    }

    @Test
    public void shouldInterruptParallelAnyWhenAddedNewCommand() {
        DebugSubsystem s = new DebugSubsystem();
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        DebugCommand c3 = new DebugCommand();
        c1.require(s);
        c3.require(s);
        Command par = any(c1, c2);
        sched.registerSubsystem(s);

        sched.add(par);
        sched.loop();
        assertEquals(par, s.currentCommand);
        assertTrue(par.isRunning());
        assertTrue(c1.isRunning());
        assertTrue(c2.isRunning());
        assertFalse(c3.isRunning());

        sched.add(c3);
        sched.loop();
        assertEquals(c3, s.currentCommand);
        assertTrue(c1.interruptedCalled > 0);
        assertTrue(c2.interruptedCalled > 0);
        assertFalse(par.isRunning());
        assertFalse(c1.isRunning());
        assertFalse(c2.isRunning());
        assertTrue(c3.isRunning());
    }

    @Test
    public void shouldInterruptSequentialWhenAddedNewCommand() {
        DebugSubsystem s = new DebugSubsystem();
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        DebugCommand c3 = new DebugCommand();
        DebugCommand c4 = new DebugCommand();
        c1.require(s);
        c4.require(s);
        Command seq = c1.then(c2);
        sched.registerSubsystem(s);

        sched.add(seq);
        sched.loop();
        assertEquals(seq, s.currentCommand);
        assertTrue(seq.isRunning());
        assertTrue(c1.isRunning());
        assertFalse(c2.isRunning());
        assertFalse(c3.isRunning());
        assertFalse(c4.isRunning());

        c1.isFinished = true;
        sched.loop();
        sched.loop();
        assertTrue(seq.isRunning());
        assertFalse(c1.isRunning());
        assertTrue(c2.isRunning());
        assertFalse(c3.isRunning());
        assertFalse(c4.isRunning());

        sched.add(c4);
        sched.loop();
        assertEquals(c4, s.currentCommand);
        assertTrue(c1.endCalled > 0);
        assertTrue(c1.interruptedCalled == 0);
        assertTrue(c2.endCalled == 0);
        assertTrue(c2.interruptedCalled > 0);
        assertTrue(c3.endCalled == 0);
        assertTrue(c3.interruptedCalled == 0);

        assertFalse(seq.isRunning());
        assertFalse(c1.isRunning());
        assertFalse(c2.isRunning());
        assertFalse(c3.isRunning());
        assertTrue(c4.isRunning());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldErrorWhenParallelAllHasIntersectingSubsystemRequirements() {
        DebugSubsystem s1 = new DebugSubsystem();
        DebugSubsystem s2 = new DebugSubsystem();
        DebugSubsystem s3 = new DebugSubsystem();
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        c1.require(s1);
        c1.require(s2);
        c2.require(s2);
        c2.require(s3);

        all(c1, c2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldErrorWhenParallelAnyHasIntersectingSubsystemRequirements() {
        DebugSubsystem s1 = new DebugSubsystem();
        DebugSubsystem s2 = new DebugSubsystem();
        DebugSubsystem s3 = new DebugSubsystem();
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        c1.require(s1);
        c1.require(s2);
        c2.require(s2);
        c2.require(s3);

        any(c1, c2);
    }

    @Test
    public void shouldNotErrorWhenSequentialHasIntersectingSubsystemRequirements() {
        DebugSubsystem s1 = new DebugSubsystem();
        DebugSubsystem s2 = new DebugSubsystem();
        DebugSubsystem s3 = new DebugSubsystem();
        DebugCommand c1 = new DebugCommand();
        DebugCommand c2 = new DebugCommand();
        c1.require(s1);
        c1.require(s2);
        c2.require(s2);
        c2.require(s3);

        c1.then(c2);
    }

}
