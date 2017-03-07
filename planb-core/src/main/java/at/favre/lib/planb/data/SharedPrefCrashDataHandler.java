package at.favre.lib.planb.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import at.favre.lib.planb.interfaces.CrashDataHandler;
import at.favre.lib.planb.util.CrashDataUtil;

/**
 * The default implementation using {@link SharedPreferences} as persistence storage.
 */
public class SharedPrefCrashDataHandler implements CrashDataHandler {
    private static final String PREF_PREFIX = "at.favre.lib.planb.1870382740324_";
    private static final String KEY_LATEST = "KEY_LATEST";
    private static final String KEY_HASNEW = "KEY_HASNEW";
    private static final int DEFAULT_CRASH_DATA_SIZE = 50;
    private static final int MAX_CRASH_DATA_SIZE = 200;

    private SharedPreferences preferences;
    private int capacity;

    public SharedPrefCrashDataHandler(Context context) {
        this(context, DEFAULT_CRASH_DATA_SIZE);
    }

    public SharedPrefCrashDataHandler(Context context, int maxCapacity) {
        if (maxCapacity > MAX_CRASH_DATA_SIZE || maxCapacity < 1) {
            throw new IllegalArgumentException("Cannot create with this capacity. Max capacity is " + MAX_CRASH_DATA_SIZE + " but " + maxCapacity + " was provided.");
        }

        preferences = context.getSharedPreferences(PREF_PREFIX, Context.MODE_PRIVATE);
        capacity = maxCapacity;
    }

    @Override
    public CrashData getLatest() {
        CrashData cd = CrashDataUtil.createCrashDataFromStringSet(preferences.getStringSet(preferences.getString(KEY_LATEST, null), null));
        preferences.edit().putBoolean(KEY_HASNEW, false).apply();
        return cd;
    }

    @NonNull
    @Override
    public List<CrashData> getAll() {
        List<CrashData> crashDataList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : preferences.getAll().entrySet()) {
            if (entry.getValue() instanceof Set) {
                crashDataList.add(CrashDataUtil.createCrashDataFromStringSet((Set<String>) entry.getValue()));
            }
        }
        return crashDataList;
    }

    @Override
    public int size() {
        return getAll().size();
    }

    @Override
    public boolean hasUnhandledCrash() {
        return preferences.getBoolean(KEY_HASNEW, false);
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void persistCrashData(CrashData cd) {
        preferences.edit()
                .putStringSet(cd.id, CrashDataUtil.createCrashDataSet(cd))
                .putString(KEY_LATEST, cd.id)
                .putBoolean(KEY_HASNEW, true)
                .commit();

        List<CrashData> all = getAll();

        if (all.size() > capacity) {
            Collections.sort(all);
            List<CrashData> overflowList = all.subList(capacity, all.size());
            SharedPreferences.Editor editor = preferences.edit();
            for (CrashData crashData : overflowList) {
                editor.remove(crashData.id);
            }
            editor.commit();
        }
    }

    @Override
    public int countOfCrashesSince(long fromTimestamp) {
        List<CrashData> crashDatas = getAll();
        int count = 0;
        for (CrashData crashData : crashDatas) {
            if (crashData.timestamp >= fromTimestamp) {
                count++;
            }
        }
        return count;
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void clear() {
        preferences.edit().clear().commit();
    }
}
