package at.favre.lib.planb.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import at.favre.lib.planb.interfaces.CrashDataHandler;

/**
 * This is a simple in memory crash data handler for production environment or
 * if persisting is not needed.
 */
public class InMemoryCrashDataHandler implements CrashDataHandler {
    private List<CrashData> crashDataList = new ArrayList<>();
    private boolean unhandled = false;

    @Nullable
    @Override
    public CrashData getLatest() {
        unhandled = false;
        if (crashDataList.isEmpty()) {
            return null;
        }
        return crashDataList.get(0);
    }

    @NonNull
    @Override
    public List<CrashData> getAll() {
        return crashDataList;
    }

    @Override
    public int size() {
        return crashDataList.size();
    }

    @Override
    public boolean hasUnhandledCrash() {
        return unhandled;
    }

    @Override
    public void persistCrashData(@NonNull CrashData crashData) {
        unhandled = true;
        crashDataList.add(crashData);
    }

    @Override
    public int countOfCrashes(long fromTimestamp) {
        int count = 0;
        for (CrashData crashData : getAll()) {
            if (crashData.timestamp >= fromTimestamp) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void clear() {
        crashDataList.clear();
    }
}
