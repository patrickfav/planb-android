package at.favre.lib.planb.data;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.EmptyStackException;

import at.favre.lib.planb.MockDataGenerator;
import at.favre.lib.planb.exceptions.MockRuntimeException;
import at.favre.lib.planb.interfaces.CrashDataHandler;

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
        assertEquals(0, handler.countOfCrashesSince(0));
        assertFalse(handler.hasUnhandledCrash());
        assertEquals(0, handler.size());
    }

    @Test
    public void testAdd() {
        CrashData crashData = createCrashData(new IllegalArgumentException());
        handler.persistCrashData(crashData);
        assertEquals(1, handler.countOfCrashesSince(0));
        assertEquals(1, handler.size());
        assertEquals(crashData, handler.getAll().get(0));
        assertTrue(handler.hasUnhandledCrash());
        assertEquals(crashData, handler.getLatest());
    }

    @Test
    public void testGetLatestAndHandledFlag() {
        CrashData crashData = createCrashData(new ArithmeticException("muh"));
        handler.persistCrashData(crashData);

        assertTrue(handler.hasUnhandledCrash());
        assertEquals(crashData, handler.getLatest());
        assertFalse(handler.hasUnhandledCrash());
        assertEquals(crashData, handler.getLatest());
    }

    @Test
    public void testClear() {
        CrashData crashData1 = createCrashData(new IllegalStateException());
        CrashData crashData2 = createCrashData(new IllegalArgumentException());
        handler.persistCrashData(crashData1);
        handler.persistCrashData(crashData2);
        assertTrue(handler.hasUnhandledCrash());
        assertEquals(2, handler.size());
        assertEquals(2, handler.getAll().size());
        handler.clear();
        assertEquals(0, handler.size());
        assertEquals(0, handler.getAll().size());
        assertFalse(handler.hasUnhandledCrash());
        assertNull(handler.getLatest());
    }

    @Test
    public void testAddMultiple() {
        int rounds = 15;
        for (int i = 0; i < rounds; i++) {
            CrashData crashData = createCrashData(new EmptyStackException());
            handler.persistCrashData(crashData);
            assertEquals(i + 1, handler.countOfCrashesSince(0));
            assertEquals(i + 1, handler.size());
            assertTrue(handler.hasUnhandledCrash());
            assertEquals(crashData, handler.getLatest());
            assertFalse(handler.hasUnhandledCrash());
            SystemClock.sleep(2);
        }
    }

    @Test
    public void testCountSince() {
        CrashData crashData1 = createCrashData(new IllegalArgumentException());
        CrashData crashData2 = createCrashData(new MockRuntimeException());
        handler.persistCrashData(crashData1);
        handler.persistCrashData(crashData2);
        assertTrue(handler.hasUnhandledCrash());
        assertEquals(2, handler.countOfCrashesSince(0));
        assertEquals(0, handler.countOfCrashesSince(System.currentTimeMillis() + 100));

    }

    public static CrashData createCrashData(Throwable t) {
        return MockDataGenerator.createCrashData(t);
    }
}
