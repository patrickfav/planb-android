package at.favre.lib.planb;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.favre.lib.planb.interfaces.CrashDataHandler;
import at.favre.lib.planb.interfaces.CrashDataHandlerFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PlanBCrashDataHandlerFactoryTest {
    private CrashDataHandlerFactory factory;

    @Before
    public void setup() {
        factory = PlanB.crashDataHandlerFactory();
        factory.createSharedPrefHandler(InstrumentationRegistry.getTargetContext()).clear();
    }

    @Test
    public void testInMemoryHandler() {
        check(factory.createInMemoryHandler());
    }

    @Test
    public void testSharedPrefHandler() {
        check(factory.createSharedPrefHandler(InstrumentationRegistry.getTargetContext()));
        check(factory.createSharedPrefHandler(InstrumentationRegistry.getTargetContext(), 10));
    }

    private void check(CrashDataHandler handler) {
        assertNotNull(handler);
        assertTrue(handler.getAll().isEmpty());
        assertNull(handler.getLatest());
        assertEquals(0, handler.countOfCrashesSince(0));
        assertFalse(handler.hasUnhandledCrash());
        assertEquals(0, handler.size());
        handler.clear();
    }
}
