package at.favre.lib.planb;

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.Map;

import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.data.CrashDataHandler;
import at.favre.lib.planb.exceptions.ICrashExceptionData;
import at.favre.lib.planb.recover.CrashRecoverBehaviour;
import at.favre.lib.planb.util.CrashDataUtil;

class PlanBUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final static String TAG = PlanBUncaughtExceptionHandler.class.getName();

    private PlanBConfig config;
    private Context context;

    PlanBUncaughtExceptionHandler(Context context, PlanBConfig config) {
        this.config = config;
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            CrashDataHandler crashDataHandler = PlanB.get().getCrashDataHandler();

            log("got uncaught exception: " + throwable.getClass().getSimpleName() + ", isDebugBuild: " + config.isDebugBuild, config.enableLog);

            CrashRecoverBehaviour behaviour = (config.isDebugBuild ? config.debugBehaviour : config.releaseBehaviour);

            Map<String, String> customCrashData = null;
            if (throwable instanceof ICrashExceptionData) {
                customCrashData = ((ICrashExceptionData) throwable).getAdditionalExceptionData();
            }

            CrashData crashData = CrashDataUtil.createFromCrash(config, thread, throwable, customCrashData);
            if (behaviour.persistCrashData()) {
                log("persist crash data", config.enableLog);
                crashDataHandler.persistCrashData(crashData);
            }

            if (crashDataHandler.countOfCrashes(new Date().getTime() - 1000) >= 3) {
                log("too many crashes in series - something seems wrong, abort custom crash ahndling", config.enableLog);
                PlanB.defaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
                return;
            }

            behaviour.getPreCrashAction().onUnhandledException(context, thread, throwable, crashData, config);

            log("handle crash", config.enableLog);

            behaviour.handleCrash(context, thread, throwable, crashData, config);

            behaviour.getPostCrashAction().onUnhandledException(context, thread, throwable, crashData, config);

            if (behaviour.callDefaultExceptionHandler()) {
                log("call default uncaughtExceptionHandler " + PlanB.defaultUncaughtExceptionHandler.getClass().getName(), config.enableLog);
                PlanB.defaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
            }

            if (behaviour.killProcess()) {
                log("kill process", config.enableLog);
                android.os.Process.killProcess(android.os.Process.myPid());
            }

            log("uncaught exception handling finish", config.enableLog);
        } catch (Exception e) {
            Log.e(TAG, "there was an exception in the crash recover logic", e);
        }
    }

    private static void log(String msg, boolean enable) {
        if (enable) {
            Log.d(TAG, msg);
        }
    }
}
