package at.favre.lib.planb.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;

/**
 * Util for crash data
 */
public class CrashDataUtil {
    private static final String DIVIDER = ":::o:::";
    private static final String DIVIDER_NULL = ":::o::null:";

    /**
     * Simple serializer of crash data to
     *
     * @param crashData
     * @return serialized key:value version of this model
     */
    public static Set<String> createCrashDataSet(CrashData crashData) {
        Set<String> set = new HashSet<>();
        for (Map.Entry<String, String> entry : crashData.createMap().entrySet()) {
            if (entry.getKey() == null) {
                throw new IllegalArgumentException("key must not be null");
            }
            if (entry.getValue() == null) {
                set.add(entry.getKey() + DIVIDER_NULL);
            } else {
                set.add(entry.getKey() + DIVIDER + entry.getValue());
            }
        }
        return set;
    }

    /**
     * Creates a crash data model from simple serialized key:value set.
     * See {@link #createCrashDataSet(CrashData)}
     *
     * @param serialized as provided from {@link #createCrashDataSet(CrashData)}
     * @return pojo
     */
    public static CrashData createCrashDataFromStringSet(Set<String> serialized) {
        if (serialized == null) {
            return null;
        }

        Map<String, String> dataMap = new HashMap<>();
        for (String s : serialized) {
            if (s.endsWith(DIVIDER_NULL)) {
                dataMap.put(s.split(DIVIDER_NULL)[0], null);
            } else {
                String[] parts = s.split(DIVIDER);
                if (parts.length == 1) {
                    dataMap.put(parts[0], "");
                } else {
                    dataMap.put(parts[0], parts[1]);
                }
            }
        }
        return CrashData.create(dataMap);
    }

    /**
     * Creates a crash model from exception data
     *
     * @param config
     * @param thread
     * @param throwable
     * @param customData
     * @return the model containing all the data provided
     */
    public static CrashData createFromCrash(PlanBConfig config, Thread thread, Throwable throwable, Map<String, String> customData) {
        if (throwable == null) {
            throw new IllegalArgumentException("throwable must not be null");
        }
        StackTraceElement element = throwable.getStackTrace()[0];

        return new CrashData(
                UUID.randomUUID().toString(),
                System.currentTimeMillis(),
                throwable.getMessage(),
                throwable.getClass().getName(),
                thread.getName() + " [" + thread.getId() + "]",
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

    /**
     * Gets the name without qualification (ie. package) of the given class name
     *
     * @param throwableClassName e.g. 'java.lang.NullPointerException'
     * @return class name without package e.g. 'NullPointerException'
     */
    public static String getClassNameForException(String throwableClassName) {
        if (throwableClassName != null && throwableClassName.contains(".")) {
            String[] parts = throwableClassName.split(Pattern.quote("."));
            return parts[parts.length - 1];
        }
        return throwableClassName;
    }

    /**
     * Formats date for given timestamp
     *
     * @param timestamp
     * @return date as string
     */
    public static String parseDate(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z", Locale.getDefault()).format(new Date(timestamp));
    }

    /**
     * Loggable summary of a list of crash data
     *
     * @param crashDataList to use for log
     * @return summary
     */
    public static StringBuilder getLogString(List<CrashData> crashDataList) {
        StringBuilder sb = new StringBuilder("==== STORED CRASH DATA (").append(crashDataList.size()).append(") ===\n");
        for (CrashData crashData : crashDataList) {
            sb.append(parseDate(crashData.timestamp)).append("\n");
            sb.append(crashData.throwableClassName).append(": ").append(crashData.message).append("\n");
            sb.append("\tcaused by ").append(crashData.causeClassName).append(".").append(crashData.causeMethodName).append("(").append(crashData.causeFileName).append(":").append(crashData.causeLineNum).append(")");
            sb.append("\n------------------------\n");
        }
        sb.append("\n");
        return sb;
    }

    /**
     * Loggable string of crash data details
     *
     * @param crashData
     * @return string
     */
    public static StringBuilder getLogString(CrashData crashData) {
        StringBuilder sb = new StringBuilder("==== RECORDED UNHANDLED EXCEPTION ==\n");
        sb.append(parseDate(crashData.timestamp)).append("\n");
        sb.append(crashData.throwableClassName).append(": ").append(crashData.message).append("\n");
        sb.append("\tVersion: ").append(crashData.versionString).append(", SCM: ").append(crashData.scmString).append(", CI: ").append(crashData.ciString).append("\n\n");
        sb.append("\t").append(crashData.fullStacktrace);
        return sb;
    }
}
