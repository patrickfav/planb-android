package at.favre.lib.planb.parser;


import android.os.Build;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import at.favre.lib.planb.data.CrashData;

/**
 * Handling the document specific placeholder
 */
public class BugReportPlaceholderHandler {
    private static final String PH_TIMESTAMP = "timestamp";
    private static final String PH_VERSION = "version";
    private static final String PH_SCM = "scm";
    private static final String PH_CI = "ci";
    private static final String PH_DEVICE = "device";
    private static final String PH_ANDROID_VERSION = "android_version";
    private static final String PH_DEVICE_SERIAL = "device_serial";
    private static final String PH_EXCEPTION_SUMMARY = "exception";
    private static final String PH_STACKTRACE = "stacktrace";

    private final Map<String, String> map;

    public BugReportPlaceholderHandler(CrashData crashData) {
        map = new HashMap<>();
        map.put(PH_TIMESTAMP, new Date(crashData.timestamp).toString());
        map.put(PH_VERSION, crashData.versionString);
        map.put(PH_SCM, crashData.scmString);
        map.put(PH_CI, crashData.ciString);
        map.put(PH_VERSION, crashData.versionString);

        map.put(PH_DEVICE, Build.MANUFACTURER + " " + Build.MODEL + " (" + Build.DEVICE + ")");
        map.put(PH_ANDROID_VERSION, Build.VERSION.RELEASE + " (" + String.valueOf(Build.VERSION.SDK_INT) + ")");
        map.put(PH_DEVICE_SERIAL, Build.SERIAL);

        map.put(PH_EXCEPTION_SUMMARY, crashData.causeClassName + ": " + crashData.message);
        map.put(PH_STACKTRACE, crashData.fullStacktrace);
    }

    public Map<String, String> getPlaceHolderMap() {
        return map;
    }
}
