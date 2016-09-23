package at.favre.lib.planb;

import android.content.Context;

import at.favre.lib.planb.data.CrashDataHandler;
import at.favre.lib.planb.data.SharedPrefStorage;

public class PlanB {

    public static PlanB get(Context context) {
        return new PlanB(context);
    }

    private Context context;

    private PlanB(Context context) {
        this.context = context;
    }

    static Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    public void enableCrashHandler(PlanBConfig config) {
        Thread.setDefaultUncaughtExceptionHandler(new PlanBUncaughtExceptionHandler(context, config));
    }

    public PlanBConfig.Builder configBuilder() {
        return new PlanBConfig.Builder(context);
    }

    public CrashDataHandler getCrashDataHandler() {
        return new SharedPrefStorage(context);
    }
}
