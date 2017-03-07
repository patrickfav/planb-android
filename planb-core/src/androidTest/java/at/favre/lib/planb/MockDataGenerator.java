package at.favre.lib.planb;


import java.util.Collections;

import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.util.CrashDataUtil;

public class MockDataGenerator {
    public static CrashData createCrashData(Throwable t) {
        return CrashDataUtil.createFromCrash(new PlanBConfig.Builder().build(),
                Thread.currentThread(), t, Collections.<String, String>emptyMap());
    }
}
