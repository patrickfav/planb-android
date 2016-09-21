package at.favre.lib.planb.util;

import android.app.ActivityManager;
import android.content.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import at.favre.lib.planb.exceptions.MockRuntimeException;

public class CrashUtil {

    public static void crash() {
        throw new MockRuntimeException();
    }

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

    private static String getExceptionSummary(String detailMsg, StackTraceElement[] stackTrace, int stackTraceHierarchy) {
        String msg = detailMsg;
        if (stackTrace.length > stackTraceHierarchy) {
            msg += " / at " + stackTrace[stackTraceHierarchy].getClassName() + "." + stackTrace[stackTraceHierarchy].getMethodName() + "(" + stackTrace[stackTraceHierarchy].getFileName() + ":" + stackTrace[stackTraceHierarchy].getLineNumber() + ")";
        }
        return msg;
    }
}
