package at.favre.lib.planb.util;


import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Set;

import at.favre.lib.planb.MockDataGenerator;
import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class CrashDataUtilTest {

    @Test
    public void testSerializeAndDeserialze() {
        CrashData crashData = MockDataGenerator.createCrashData(new IllegalStateException("the message"));
        serializeAndDeserializeAndCheck(crashData);
    }

    @Test
    public void testSerializeAndDeserialzeWithNullMessage() {
        CrashData crashData = MockDataGenerator.createCrashData(new IllegalStateException());
        serializeAndDeserializeAndCheck(crashData);
    }

    private void serializeAndDeserializeAndCheck(CrashData crashData) {
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

    @Test
    public void testClassNameForException() {
        assertEquals("NullPointerException", CrashDataUtil.getClassNameForException("java.lang.NullPointerException"));
        assertEquals("NullPointerException", CrashDataUtil.getClassNameForException("NullPointerException"));
        assertEquals("g", CrashDataUtil.getClassNameForException("a.b.c.d.e.f.g"));
    }

    @Test
    public void testGetLogStrings() {
        String logSingle = CrashDataUtil.getLogString(MockDataGenerator.createCrashData(new EmptyStackException())).toString();
        assertNotNull(logSingle);
        assertTrue(logSingle.contains(EmptyStackException.class.getName()));

        List<CrashData> crashDatas = Arrays.asList(MockDataGenerator.createCrashData(new IllegalStateException()), MockDataGenerator.createCrashData(new ArithmeticException()));
        String logAll = CrashDataUtil.getLogString(crashDatas).toString();
        assertNotNull(logAll);
        assertTrue(logAll.contains(IllegalStateException.class.getName()));
        assertTrue(logAll.contains(ArithmeticException.class.getName()));
    }
}
