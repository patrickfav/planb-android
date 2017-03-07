package at.favre.lib.planb.data;

import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;

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

        Parcel p = Parcel.obtain();
        crashData.writeToParcel(p, 0);
        CrashData deserialized = CrashData.CREATOR.createFromParcel(p);
        p.recycle();
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
    public void testSort() {
        List<CrashData> crashDataList = new ArrayList<>();
        crashDataList.add(MockDataGenerator.createCrashData(new NullPointerException()));
        SystemClock.sleep(2);
        crashDataList.add(MockDataGenerator.createCrashData(new IllegalArgumentException()));
        SystemClock.sleep(2);
        crashDataList.add(MockDataGenerator.createCrashData(new EmptyStackException()));
        SystemClock.sleep(2);
        crashDataList.add(MockDataGenerator.createCrashData(new IllegalStateException()));

        List<CrashData> copy = new ArrayList<>(crashDataList);
        Collections.shuffle(copy);
        Collections.sort(copy);

        for (int i = 0; i < copy.size(); i++) {
            assertEquals(copy.get(i), crashDataList.get(i));
        }
    }

    @Test
    public void testToString() {
        CrashData crashData1 = MockDataGenerator.createCrashData(new RemoteException());
        assertNotNull(crashData1.toString());
    }
}
