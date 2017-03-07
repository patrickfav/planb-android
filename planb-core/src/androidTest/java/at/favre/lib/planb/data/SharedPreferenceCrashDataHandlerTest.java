package at.favre.lib.planb.data;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.interfaces.CrashDataHandler;
import at.favre.lib.planb.util.CrashDataUtil;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SharedPreferenceCrashDataHandlerTest {
    private CrashDataHandler handler;

    @Before
    public void setup() {
        handler = new SharedPrefCrashDataHandler(InstrumentationRegistry.getTargetContext());
        handler.clear();
    }

    @Test
    public void testDefaults() {
        assertNotNull(handler);
        assertTrue(handler.getAll().isEmpty());
        assertNull(handler.getLatest());
        assertEquals(0, handler.countOfCrashes(0));
        assertFalse(handler.hasUnhandledCrash());
        assertEquals(0, handler.size());
    }

    @Test
    public void testAdd() {
        CrashData crashData = createCrashData(new IllegalArgumentException());
        handler.persistCrashData(crashData);
        assertEquals(1, handler.countOfCrashes(0));
        assertEquals(1, handler.size());
        assertEquals(crashData, handler.getAll().get(0));
        assertTrue(handler.hasUnhandledCrash());
        assertEquals(crashData, handler.getLatest());
    }

    @Test
    public void testAddMultiple() {
        int rounds = 15;
        for (int i = 0; i < rounds; i++) {
            CrashData crashData = createCrashData(new IllegalArgumentException());
            handler.persistCrashData(crashData);
            assertEquals(i + 1, handler.countOfCrashes(0));
            assertEquals(i + 1, handler.size());
            assertTrue(handler.hasUnhandledCrash());
            assertEquals(crashData, handler.getLatest());
            assertFalse(handler.hasUnhandledCrash());
            SystemClock.sleep(2);
        }
    }

    public static CrashData createCrashData(Throwable t) {
        return CrashDataUtil.createFromCrash(new PlanBConfig.Builder().build(),
                Thread.currentThread(), t, Collections.<String, String>emptyMap());
    }
}
