package at.favre.lib.planb;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import at.favre.lib.planb.data.InMemoryCrashDataHandler;
import at.favre.lib.planb.data.SharedPrefCrashDataHandler;
import at.favre.lib.planb.interfaces.CrashDataHandler;
import at.favre.lib.planb.interfaces.CrashDataHandlerFactory;
import at.favre.lib.planb.interfaces.CrashRecoverBehaviour;
import at.favre.lib.planb.interfaces.RecoverBehaviorFactory;
import at.favre.lib.planb.recover.DefaultBehavior;
import at.favre.lib.planb.recover.StartActivityBehaviour;
import at.favre.lib.planb.recover.SuppressCrashBehaviour;

/**
 * PlanB is a local crash reporting and recovery library.
 * <p>
 * This is the main API of the library.
 * <p>
 * Note: if the app crashes with an unhandled exception, all classes
 * will lose their state. So any code executed after handling he exception
 * will be called with a new {@link android.app.Application} object.
 */
public final class PlanB {
    private final static RecoverBehaviorFactory BEHAVIOR_FACTORY = new DefaultRecoverBehaviorFactor();
    private final static CrashDataHandlerFactory CRASH_DATA_HANDLER_FACTORY = new DefaultCrashDataHandlerFactory();
    private static PlanB instance;
    static final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    /**
     * @return factory for creating crash recover behaviour
     */
    public static RecoverBehaviorFactory behaviourFactory() {
        return BEHAVIOR_FACTORY;
    }

    /**
     * @return factory for creating crash handler (which persist crashes)
     */
    public static CrashDataHandlerFactory crashDataHandlerFactory() {
        return CRASH_DATA_HANDLER_FACTORY;
    }

    /**
     * Gets an instance of the singleton
     */
    public static PlanB get() {
        if (instance == null) {
            instance = new PlanB();
        }
        return instance;
    }

    private PlanBConfig config;
    private boolean isDebugBuild;

    private PlanB() {
    }

    /**
     * Init the library. This is required before any other feature can be used.
     * Will create a default {@link CrashDataHandler}. Internally checks the manifest
     * if this is an debuggable build.
     *
     * @param context
     */
    public void init(@NonNull Context context) {
        boolean debugBuild = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        init(context, debugBuild);
    }

    /**
     * Init the library. This is required before any other feature can be used.
     * Will create a default {@link CrashDataHandler}.
     *
     * @param context
     * @param debugBuild if this is a debug or release build; usually BuildConfig.DEBUG is the correct boolean to pass here
     */
    public void init(@NonNull Context context, boolean debugBuild) {
        init(debugBuild, newConfig(context).build());
    }

    /**
     * Init the library. This is required before any other feature can be used.
     * Will create a default {@link CrashDataHandler}.
     *
     * @param debugBuild if this is a debug or release build; usually BuildConfig.DEBUG is the correct boolean to pass here
     * @param config     configure versions and build info aswell as crash handler
     */
    public void init(boolean debugBuild, @NonNull PlanBConfig config) {
        this.config = config;
        this.isDebugBuild = debugBuild;
    }

    /**
     * Enables the crash handling logic, setting the config and behaviour.
     * <p>
     * This method will set itself as {@link Thread#getDefaultUncaughtExceptionHandler()}
     * so be aware if you have other crash reporting frameworks like HokeyApp, Crashlytics
     * or ACRA.
     * <p>
     * This call requires that {@link #init(Context, boolean)} was called first.
     *
     * @param behaviour what exactly to do when a crash occurs
     * @param context   see {@link #newConfig(Context)}
     */
    public void enableCrashHandler(Context context, CrashRecoverBehaviour behaviour) {
        enableCrashHandler(context, behaviour, behaviour);
    }

    /**
     * Enables the crash handling logic, setting the config and behaviour.
     * <p>
     * This method will set itself as {@link Thread#getDefaultUncaughtExceptionHandler()}
     * so be aware if you have other crash reporting frameworks like HokeyApp, Crashlytics
     * or ACRA.
     * <p>
     * This call requires that {@link #init(Context, boolean)} was called first.
     *
     * @param debugCrashBehaviour   behaviour for debug builds
     * @param releaseCrashBehaviour behaviour for release builds
     * @param context               see {@link #newConfig(Context)}
     */
    public void enableCrashHandler(Context context, CrashRecoverBehaviour debugCrashBehaviour, CrashRecoverBehaviour releaseCrashBehaviour) {
        checkIfInit();
        Thread.setDefaultUncaughtExceptionHandler(
                new PlanBUncaughtExceptionHandler(context, config, isDebugBuild ? debugCrashBehaviour : releaseCrashBehaviour));
    }

    /**
     * Disables the defined crash handler and sets the default one.
     */
    public void disableCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(defaultUncaughtExceptionHandler);
    }

    /**
     * Returns the value passed or (or gathered) during {@link #init(Context, boolean)}
     *
     * @return true if this is a debuggable build
     */
    public boolean isDebugBuild() {
        return isDebugBuild;
    }

    /**
     * Creates a new config builder
     *
     * @param context
     * @return builder
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
        return isDebugBuild ? config.debugCrashDataHandler : config.releaseCrashDataHandler;
    }

    private void checkIfInit() {
        if (config == null) {
            throw new IllegalStateException("you need to init the lib first with PlanB.get().init(...)");
        }
    }

    private final static class DefaultCrashDataHandlerFactory implements CrashDataHandlerFactory {
        @Override
        public CrashDataHandler createSharedPrefHandler(Context ctx) {
            return new SharedPrefCrashDataHandler(ctx);
        }

        @Override
        public CrashDataHandler createSharedPrefHandler(Context ctx, int maxCapacity) {
            return new SharedPrefCrashDataHandler(ctx, maxCapacity);
        }

        @Override
        public CrashDataHandler createInMemoryHandler() {
            return new InMemoryCrashDataHandler();
        }
    }

    private final static class DefaultRecoverBehaviorFactor implements RecoverBehaviorFactory {
        @Override
        public CrashRecoverBehaviour createSuppressCrashBehaviour() {
            return new SuppressCrashBehaviour();
        }

        @Override
        public CrashRecoverBehaviour createSuppressCrashBehaviour(@Nullable CrashRecoverBehaviour.CrashAction prePostAction, @Nullable CrashRecoverBehaviour.CrashAction postCrashAction) {
            return new SuppressCrashBehaviour(prePostAction, postCrashAction);
        }

        @Override
        public CrashRecoverBehaviour createDefaultHandlerBehaviour() {
            return new DefaultBehavior();
        }

        @Override
        public CrashRecoverBehaviour createDefaultHandlerBehaviour(@Nullable CrashRecoverBehaviour.CrashAction prePostAction, @Nullable CrashRecoverBehaviour.CrashAction postCrashAction) {
            return new DefaultBehavior(prePostAction, postCrashAction);
        }

        @Override
        public CrashRecoverBehaviour createRestartForegroundActivityCrashBehaviour() {
            return new StartActivityBehaviour(StartActivityBehaviour.FOREGROUND_ACTIVITY);
        }

        @Override
        public CrashRecoverBehaviour createRestartLauncherActivityCrashBehaviour() {
            return new StartActivityBehaviour(StartActivityBehaviour.LAUNCHER_ACTIVTY);
        }

        @Override
        public CrashRecoverBehaviour createStartActivityCrashBehaviour(Intent intent) {
            return new StartActivityBehaviour(intent);
        }

        @Override
        public CrashRecoverBehaviour createRestartForegroundActivityCrashBehaviour(@Nullable CrashRecoverBehaviour.CrashAction prePostAction, @Nullable CrashRecoverBehaviour.CrashAction postCrashAction) {
            return new StartActivityBehaviour(StartActivityBehaviour.FOREGROUND_ACTIVITY, prePostAction, postCrashAction);
        }

        @Override
        public CrashRecoverBehaviour createStartActivityCrashBehaviour(Intent intent, @Nullable CrashRecoverBehaviour.CrashAction prePostAction, @Nullable CrashRecoverBehaviour.CrashAction postCrashAction) {
            return new StartActivityBehaviour(intent, prePostAction, postCrashAction);
        }
    }
}
