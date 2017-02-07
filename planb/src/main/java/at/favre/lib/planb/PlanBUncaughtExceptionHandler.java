package at.favre.lib.planb;

import android.content.Context;
import android.util.Log;

import java.util.Map;

import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.exceptions.ICrashExceptionData;
import at.favre.lib.planb.recover.RecoverBehaviour;
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
            log("got uncaught exception: " + throwable.getClass().getSimpleName() + ", isDebugBuild: " + config.isDebugBuild, config.enableLog);

            RecoverBehaviour behaviour = (config.isDebugBuild ? config.debugBehaviour : config.releaseBehaviour);

            Map<String, String> customCrashData = null;
            if (throwable instanceof ICrashExceptionData) {
                customCrashData = ((ICrashExceptionData) throwable).getAdditionalExceptionData();
            }

            CrashData crashData = CrashDataUtil.createFromCrash(config, thread, throwable, customCrashData);
            if (behaviour.persistCrashData()) {
                log("persist crash data", config.enableLog);
                config.storage.persistCrashData(crashData);
            }

            behaviour.getPreCrashAction().onUnhandledException(context, thread, throwable, crashData);

            log("handle crash", config.enableLog);

            behaviour.handleCrash(context, thread, throwable, crashData);

            behaviour.getPostCrashAction().onUnhandledException(context, thread, throwable, crashData);

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
