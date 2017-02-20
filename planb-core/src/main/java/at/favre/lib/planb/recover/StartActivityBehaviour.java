package at.favre.lib.planb.recover;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;

/**
 * Will restart the app with a defined activity.
 */
public class StartActivityBehaviour extends BaseCrashBehaviour {
    private final static String TAG = StartActivityBehaviour.class.getName();
    private final static int DEFAULT_ACTIVITY_FLAGS = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FOREGROUND_ACTIVITY, LAUNCHER_ACTIVTY})
    public @interface ActivityType {
    }

    public static final int FOREGROUND_ACTIVITY = 0;
    public static final int LAUNCHER_ACTIVTY = 1;

    public static final String KEY_CRASHDATA = "CRASHDATA";
    public static final String KEY_BUGREPORT_SYNTAX = "BUGREPORT_SYNTAX";

    private Intent intent;
    @ActivityType
    private Integer activityType;

    private StartActivityBehaviour(Intent intent, @ActivityType Integer activityType, @Nullable CrashAction preCrashAction, @Nullable CrashAction postCrashAction) {
        super(true, false, true, preCrashAction, postCrashAction);
        this.intent = intent;
        this.activityType = activityType;
    }

    /**
     * Restarts the app and start given intent
     *
     * @param intent
     * @param preCrashAction
     * @param postCrashAction
     */
    public StartActivityBehaviour(Intent intent, @Nullable CrashAction preCrashAction, @Nullable CrashAction postCrashAction) {
        this(intent, null, preCrashAction, postCrashAction);
    }

    /**
     * Restarts the app and start given intent
     *
     * @param intent
     */
    public StartActivityBehaviour(Intent intent) {
        this(intent, new CrashAction.Noop(), new CrashAction.Noop());
    }

    /**
     * Will restart the app with current foreground or launcher activity
     *
     * @param activityType    if the launcher or foreground activity should be restarted
     * @param preCrashAction
     * @param postCrashAction
     */
    public StartActivityBehaviour(@ActivityType int activityType, @Nullable CrashAction preCrashAction, @Nullable CrashAction postCrashAction) {
        this(null, activityType, preCrashAction, postCrashAction);
    }

    /**
     * Will restart the app with current foreground or launcher activity
     *
     * @param activityType if the launcher or foreground activity should be restarted
     */
    public StartActivityBehaviour(@ActivityType int activityType) {
        this(activityType, null, null);
    }

    @Override
    public void handleCrash(@NonNull Context context, @NonNull Thread thread, @NonNull Throwable throwable, @NonNull CrashData crashData, @NonNull PlanBConfig config) {
        if (intent == null) {
            try {
                if (activityType == FOREGROUND_ACTIVITY) {
                    intent = getForegroundActivityIntent(context);
                } else if (activityType == LAUNCHER_ACTIVTY) {
                    intent = getLauncherIntent(context);
                }

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
