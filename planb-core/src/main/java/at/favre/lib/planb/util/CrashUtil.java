package at.favre.lib.planb.util;

import android.app.ActivityManager;
import android.content.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import at.favre.lib.planb.exceptions.MockRuntimeException;

/**
 * Util for handling crashes and exceptions
 */
public class CrashUtil {

    /**
     * Throws a mock runtime exception for testing
     */
    public static void crash() {
        throw new MockRuntimeException();
    }

    /**
     * @param context
     * @return true if the current app is in background
     */
    public static boolean isAppInBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return true;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }

    /**
     * Prints the stacktrace of a throwable to a string
     *
     * @param t
     * @return the stacktrace as string
     */
    public static String printStacktrace(Throwable t) {
        StringWriter sw = null;
        try {
            sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
