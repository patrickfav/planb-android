package at.favre.lib.planb.util;

import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;


public class CrashDataUtil {
    private static final String DIVIDER = ":::o:::";

    public static Set<String> createCrashDataSet(CrashData crashData) {
        Set<String> set = new HashSet<>();
        for (Map.Entry<String, String> entry : crashData.createMap().entrySet()) {
            set.add(entry.getKey() + DIVIDER + entry.getValue());
        }
        return set;
    }

    public static CrashData createCrashDataFromStringSet(Set<String> serialized) {
        if (serialized == null) {
            return null;
        }

        Map<String, String> dataMap = new HashMap<>();
        for (String s : serialized) {
            String[] parts = s.split(DIVIDER);
            dataMap.put(parts[0], parts[1]);
        }
        return CrashData.create(dataMap);
    }

    public static Bundle createCrashDataBundle(CrashData crashData) {
        Bundle b = new Bundle();
        for (Map.Entry<String, String> entry : crashData.createMap().entrySet()) {
            b.putString(entry.getKey(), entry.getValue());
        }
        return b;
    }

    public static CrashData createFromCrash(PlanBConfig config, Thread thread, Throwable throwable, Map<String, String> customData) {
        if (throwable == null) {
            throw new IllegalArgumentException("throwable must not be null");
        }
        StackTraceElement element = throwable.getStackTrace()[0];

        return new CrashData(
                UUID.randomUUID().toString(),
                new Date().getTime(),
                throwable.getMessage(),
                throwable.getClass().getName(),
                thread.getName(),
                element.getClassName(),
                element.getMethodName(),
                element.getFileName(),
                element.getLineNumber(),
                CrashUtil.printStacktrace(throwable),
                config.versionName + " (" + config.versionCode + ")",
                config.appBuiltType + (config.appFlavour != null && !config.appFlavour.isEmpty() ? (":" + config.appFlavour) : ""),
                config.scmRevHash != null ? config.scmRevHash.substring(0, Math.min(8, config.scmRevHash.length())) + " (" + config.scmBranch + ")" : "",
                config.ciBuildId != null ? config.ciBuildId + (config.ciBuildJob != null ? " / " + config.ciBuildJob : "") : "",
                customData);
    }

    public static String getClassNameForException(String throwableClassName) {
        if (throwableClassName != null && throwableClassName.contains(".")) {
            String[] parts = throwableClassName.split(Pattern.quote("."));
            return parts[parts.length - 1];
        }
        return throwableClassName;
    }

    public static String parseDate(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z", Locale.getDefault()).format(new Date(timestamp));
    }
}
