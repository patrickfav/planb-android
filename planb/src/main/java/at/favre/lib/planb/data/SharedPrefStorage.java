package at.favre.lib.planb.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import at.favre.lib.planb.util.CrashDataUtil;


public class SharedPrefStorage implements PersistenceStorage, CrashDataHandler {
    private static final String PREF_PREFIX = "at.favre.lib.planb.1870382740324_";
    private static final String KEY_LATEST = "KEY_LATEST";
    private static final String KEY_HASNEW = "KEY_HASNEW";
    private static final int MAX_CRASH_DATA_SIZE = 10;

    private SharedPreferences preferences;
    private int capacity;

    public SharedPrefStorage(Context context) {
        this(context, MAX_CRASH_DATA_SIZE);
    }

    public SharedPrefStorage(Context context, int maxCapacity) {
        preferences = context.getSharedPreferences(PREF_PREFIX, Context.MODE_PRIVATE);
        capacity = maxCapacity;
    }

    @Override
    public CrashData getLatest() {
        CrashData cd = CrashDataUtil.createCrashDataFromStringSet(preferences.getStringSet(preferences.getString(KEY_LATEST, null), null));
        preferences.edit().putBoolean(KEY_HASNEW, false);
        return cd;
    }

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
    public boolean hasUnhandledCrash() {
        return preferences.getBoolean(KEY_HASNEW, false);
    }

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
            List<CrashData> overflowList = all.subList(capacity - 1, all.size());
            for (CrashData crashData : overflowList) {
                preferences.edit().remove(crashData.id);
            }
        }
    }

    @Override
    public void clear() {
        preferences.edit().clear().commit();
    }
}
