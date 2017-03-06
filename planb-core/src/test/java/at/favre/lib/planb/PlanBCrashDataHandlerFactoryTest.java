package at.favre.lib.planb;


import org.junit.Before;
import org.junit.Test;

import at.favre.lib.planb.interfaces.CrashDataHandler;
import at.favre.lib.planb.interfaces.CrashDataHandlerFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class PlanBCrashDataHandlerFactoryTest {
    private CrashDataHandlerFactory factory;

    @Before
    public void setup() {
        factory = PlanB.crashDataHandlerFactory();
    }

    @Test
    public void testInMemoryHandler() {
        check(factory.createInMemoryHandler());
    }

    private void check(CrashDataHandler handler) {
        assertNotNull(handler);
        assertTrue(handler.getAll().isEmpty());
        assertNull(handler.getLatest());
        assertEquals(0, handler.countOfCrashes(0));
        assertFalse(handler.hasUnhandledCrash());
        assertEquals(0, handler.size());
        handler.clear();
    }
}
