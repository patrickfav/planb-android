package at.favre.lib.planb.util;


import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class CrashDataUtilTest {

    @Test
    public void testSerializeAndDeserialze() {
        CrashData crashData = CrashDataUtil.createFromCrash(new PlanBConfig.Builder().build(), Thread.currentThread(), new IllegalStateException(), Collections.<String, String>emptyMap());

        Set<String> serialized = CrashDataUtil.createCrashDataSet(crashData);
        assertFalse(serialized.isEmpty());
        CrashData deserialized = CrashDataUtil.createCrashDataFromStringSet(serialized);
        assertEquals(crashData, deserialized);
    }

    @Test
    public void testCreateCrashData() {
        CrashData crashData = CrashDataUtil.createFromCrash(new PlanBConfig.Builder().build(), Thread.currentThread(), new IllegalStateException(), Collections.<String, String>emptyMap());
        assertEquals(IllegalStateException.class.getName(), crashData.throwableClassName);
        assertTrue(crashData.threadName.startsWith(Thread.currentThread().getName()));
    }
}
