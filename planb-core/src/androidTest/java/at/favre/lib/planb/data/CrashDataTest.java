package at.favre.lib.planb.data;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import at.favre.lib.planb.MockDataGenerator;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CrashDataTest {
    @Test
    public void testParcelable() {
        CrashData crashData = MockDataGenerator.createCrashData(new NullPointerException());
        Bundle b = new Bundle();
        b.putParcelable("test", crashData);
        assertEquals(crashData, b.getParcelable("test"));
    }

    @Test
    public void testHashCodeAndEquals() {
        CrashData crashData1 = MockDataGenerator.createCrashData(new NullPointerException());
        CrashData crashData2 = MockDataGenerator.createCrashData(new IllegalArgumentException());

        assertFalse(crashData1.equals(crashData2));
        assertTrue(crashData1.equals(crashData1));
        assertFalse(crashData1.hashCode() != crashData1.hashCode());
    }

    @Test
    public void testToString() {
        CrashData crashData1 = MockDataGenerator.createCrashData(new RemoteException());
        assertNotNull(crashData1.toString());
    }
}
