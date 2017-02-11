package at.favre.lib.planb;

import android.content.Context;
import android.support.annotation.NonNull;

import at.favre.lib.planb.data.CrashDataHandler;
import at.favre.lib.planb.data.SharedPrefCrashDataHandler;

/**
 * PlanB is a local crash reporting and recovery library.
 * <p>
 * This is the main API of the library.
 * <p>
 * Note: if the app crashes with an unhandled exception, all classes
 * will lose their state. So any code executed after handling he exception
 * will be called with a new {@link android.app.Application} object. S
 */
public final class PlanB {

    private static PlanB instance;
    static Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    /**
     * Gets an instance of the singleton
     */
    public static PlanB get() {
        if (instance == null) {
            instance = new PlanB();
        }
        return instance;
    }

    private CrashDataHandler crashDataHandler;

    private PlanB() {
    }

    /**
     * Init the library. This is required before any other feature can be used.
     * Will create a default {@link CrashDataHandler}.
     *
     * @param context
     */
    public void init(@NonNull Context context) {
        init(context, new SharedPrefCrashDataHandler(context));
    }

    /**
     * Init the library. This is required before any other feature can be used.
     * Use this if you want to provide a custom implementation of a {@link CrashDataHandler}.
     *
     * @param context
     * @param crashDataHandler a custom implementation
     */
    public void init(@NonNull Context context, @NonNull CrashDataHandler crashDataHandler) {
        this.crashDataHandler = crashDataHandler;
    }

    /**
     * Enables the crash handling logic, setting the config and behaviour.
     * <p>
     * This method will set itself as {@link Thread#getDefaultUncaughtExceptionHandler()}
     * so be aware if you have other crash reporting frameworks like HokeyApp, Crashlytics
     * or ACRA.
     * <p>
     * This call requires that {@link #init(Context)} was called first.
     *
     * @param config  mandatory config to provide app's version data
     * @param context see {@link #newConfig(Context)}
     */
    public void enableCrashHandler(PlanBConfig config, Context context) {
        checkIfInit();
        Thread.setDefaultUncaughtExceptionHandler(new PlanBUncaughtExceptionHandler(context, config));
    }

    /**
     * Disables the defined crash handler and sets the default one.
     */
    public void disableCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(defaultUncaughtExceptionHandler);
    }

    /**
     * Creates a new config builder
     *
     * @param context
     * @return
     */
    public PlanBConfig.Builder newConfig(Context context) {
        return PlanBConfig.newBuilder(context);
    }

    /**
     * Get the current crash data handler implementation. Only works
     * after initialization.
     */
    public CrashDataHandler getCrashDataHandler() {
        checkIfInit();
        return crashDataHandler;
    }

    private void checkIfInit() {
        if (crashDataHandler == null) {
            throw new IllegalStateException("you need to init the lib first with PlanB.get().init(...)");
        }
    }
}
