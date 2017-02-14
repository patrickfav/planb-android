package at.favre.lib.planb.recover;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;

/**
 * Will restart the app with a defined activity.
 */
public class RestartActivityBehaviour extends BaseCrashBehaviour {
    private final static String TAG = RestartActivityBehaviour.class.getName();
    private final static int DEFAULT_ACTIVITY_FLAGS = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;

    public static final String KEY_CRASHDATA = "CRASHDATA";
    public static final String KEY_BUGREPORT_SYNTAX = "BUGREPORT_SYNTAX";

    private Intent intent;

    /**
     * Restarts the app and start given intent
     *
     * @param intent
     * @param preCrashAction
     * @param postCrashAction
     */
    public RestartActivityBehaviour(Intent intent, CrashAction preCrashAction, CrashAction postCrashAction) {
        super(true, false, true, preCrashAction, postCrashAction);
        this.intent = intent;
    }

    /**
     * Restarts the app and start given intent
     *
     * @param intent
     */
    public RestartActivityBehaviour(Intent intent) {
        this(intent, new CrashAction.Noop(), new CrashAction.Noop());
    }

    /**
     * Will restart the app with current foreground activity
     *
     * @param preCrashAction
     * @param postCrashAction
     */
    public RestartActivityBehaviour(CrashAction preCrashAction, CrashAction postCrashAction) {
        this(null, preCrashAction, postCrashAction);
    }

    /**
     * Will restart the app with current foreground activity
     */
    public RestartActivityBehaviour() {
        this(null);
    }

    @Override
    public void handleCrash(@NonNull Context context, @NonNull Thread thread, @NonNull Throwable throwable, @NonNull CrashData crashData, @NonNull PlanBConfig config) {
        if (intent == null) {
            try {
                intent = getForegroundActivityIntent(context);
                if (intent == null) {
                    intent = getLauncherIntent(context);
                }
            } catch (Exception e) {
                Log.e(TAG, "Could not get intent for current foreground activity", e);
                return;
            }
        }

        intent.putExtra(KEY_CRASHDATA, crashData);
        intent.putExtra(KEY_BUGREPORT_SYNTAX, config.bugReportMarkupLanguage);
        intent.addFlags(DEFAULT_ACTIVITY_FLAGS);
        context.startActivity(intent);
    }

    private Intent getLauncherIntent(Context context) {
        return context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
    }

    private Intent getForegroundActivityIntent(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.GET_TASKS") != PackageManager.PERMISSION_GRANTED) {
            throw new IllegalStateException("Checking the current activity requires 'android.permission.GET_TASKS' permisison. See http://stackoverflow.com/questions/3873659");
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName componentName;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            componentName = am.getAppTasks().get(0).getTaskInfo().topActivity;
        } else {
            componentName = am.getRunningTasks(1).get(0).topActivity;
        }

        if (componentName == null) {
            throw new IllegalStateException("Could not find componentName for current foreground activity");
        }

        try {
            intent = new Intent(context, Class.forName(componentName.getClassName()));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Could not create current foreground activity, could not find " + componentName.getPackageName());
        }
        return intent;
    }
}
