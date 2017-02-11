package at.favre.lib.planb;

import android.content.Context;

import at.favre.lib.planb.data.CrashDataHandler;
import at.favre.lib.planb.data.SharedPrefStorage;

public final class PlanB {

    private static PlanB instance;
    static Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    public static PlanB get() {
        if (instance == null) {
            instance = new PlanB();
        }
        return instance;
    }

    private PlanBConfig config;
    private CrashDataHandler crashDataHandler;
    private PlanB() {
    }

    public void init(Context context) {
        this.crashDataHandler = new SharedPrefStorage(context);
    }

    public void init(Context context, CrashDataHandler crashDataHandler) {
        this.crashDataHandler = crashDataHandler;
    }

    public void enableCrashHandler(PlanBConfig config, Context context) {
        this.config = config;
        Thread.setDefaultUncaughtExceptionHandler(new PlanBUncaughtExceptionHandler(context, config));
    }

    public void disableCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(defaultUncaughtExceptionHandler);
    }

    public PlanBConfig.Builder configBuilder(Context context) {
        return PlanBConfig.newBuilder(context);
    }

    public CrashDataHandler getCrashDataHandler() {
        if (crashDataHandler == null) {
            throw new IllegalStateException("you need to init the lib first with PlanB.get().init(...)");
        }
        return crashDataHandler;
    }
}
