package at.favre.lib.planb.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CrashData implements Comparable<CrashData> {
    private static final String MAP_SPLIT = ":";
    private static final String ARG_ID = "ARG_ID";
    private static final String ARG_DATE = "ARG_DATE";
    private static final String ARG_FULLSTACK = "ARG_FULLSTACK";
    private static final String ARG_MSG = "ARG_MSG";
    private static final String ARG_THROWABLE_CLASS_NAME = "ARG_THROWABLE_CLASS_NAME";
    private static final String ARG_THREAD_NAME = "ARG_THREAD_NAME";
    private static final String ARG_CAUSE_CLASS_NAME = "ARG_CAUSE_CLASS_NAME";
    private static final String ARG_CAUSE_METHOD_NAME = "ARG_CAUSE_METHOD_NAME";
    private static final String ARG_CAUSE_FILE_NAME = "ARG_CAUSE_FILE_NAME";
    private static final String ARG_CAUSE_LINE_NUM = "ARG_CAUSE_LINE_NUM";
    private static final String ARG_CUSTOM_MAP = "ARG_CUSTOM_MAP";

    public final String id;

    public final long timestamp;

    public final String message;
    public final String throwableClassName;
    public final String threadName;

    public final String causeClassName;
    public final String causeMethodName;
    public final String causeFileName;
    public final int causeLineNum;

    public final String fullStacktrace;

    public Map<String, String> customData;

    public CrashData(String id, long timestamp, String message, String throwableClassName, String threadName,
                     String causeClassName, String causeMethodName, String causeFileName, int causeLineNum,
                     String fullStacktrace, Map<String, String> customData) {
        this.id = id;
        this.timestamp = timestamp;
        this.message = message;
        this.causeClassName = causeClassName;
        this.causeMethodName = causeMethodName;
        this.causeFileName = causeFileName;
        this.causeLineNum = causeLineNum;
        this.fullStacktrace = fullStacktrace;
        this.throwableClassName = throwableClassName;
        this.threadName = threadName;
        this.customData = (customData == null ? Collections.<String, String>emptyMap() : customData);
    }

    public static CrashData create(Map<String, String> map) {
        Map<String, String> customData = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().startsWith(ARG_CUSTOM_MAP)) {
                customData.put(entry.getKey().split(MAP_SPLIT)[1], entry.getValue());
            }
        }
        return new CrashData(
                map.get(ARG_ID),
                Long.valueOf(map.get(ARG_DATE)),
                map.get(ARG_MSG),
                map.get(ARG_THROWABLE_CLASS_NAME),
                map.get(ARG_THREAD_NAME),
                map.get(ARG_CAUSE_CLASS_NAME),
                map.get(ARG_CAUSE_METHOD_NAME),
                map.get(ARG_CAUSE_FILE_NAME),
                Integer.valueOf(map.get(ARG_CAUSE_LINE_NUM)),
                map.get(ARG_FULLSTACK),
                customData);
    }

    public Map<String, String> createMap() {
        Map<String, String> map = new HashMap<>();
        map.put(ARG_ID, id);
        map.put(ARG_DATE, String.valueOf(timestamp));

        map.put(ARG_MSG, message);
        map.put(ARG_THROWABLE_CLASS_NAME, throwableClassName);
        map.put(ARG_THREAD_NAME, threadName);
        map.put(ARG_CAUSE_CLASS_NAME, causeClassName);
        map.put(ARG_CAUSE_METHOD_NAME, causeMethodName);
        map.put(ARG_CAUSE_FILE_NAME, causeFileName);
        map.put(ARG_CAUSE_LINE_NUM, String.valueOf(causeLineNum));
        map.put(ARG_FULLSTACK, fullStacktrace);

        for (Map.Entry<String, String> entry : customData.entrySet()) {
            if (entry.getKey() == null) {
                throw new IllegalStateException("custom map key must not be null");
            }
            map.put(ARG_CUSTOM_MAP + MAP_SPLIT + entry.getKey(), entry.getValue());
        }

        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CrashData crashData = (CrashData) o;

        if (timestamp != crashData.timestamp) return false;
        if (causeLineNum != crashData.causeLineNum) return false;
        if (id != null ? !id.equals(crashData.id) : crashData.id != null) return false;
        if (message != null ? !message.equals(crashData.message) : crashData.message != null)
            return false;
        if (throwableClassName != null ? !throwableClassName.equals(crashData.throwableClassName) : crashData.throwableClassName != null)
            return false;
        if (threadName != null ? !threadName.equals(crashData.threadName) : crashData.threadName != null)
            return false;
        if (causeClassName != null ? !causeClassName.equals(crashData.causeClassName) : crashData.causeClassName != null)
            return false;
        if (causeMethodName != null ? !causeMethodName.equals(crashData.causeMethodName) : crashData.causeMethodName != null)
            return false;
        if (causeFileName != null ? !causeFileName.equals(crashData.causeFileName) : crashData.causeFileName != null)
            return false;
        if (fullStacktrace != null ? !fullStacktrace.equals(crashData.fullStacktrace) : crashData.fullStacktrace != null)
            return false;
        return customData != null ? customData.equals(crashData.customData) : crashData.customData == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (throwableClassName != null ? throwableClassName.hashCode() : 0);
        result = 31 * result + (threadName != null ? threadName.hashCode() : 0);
        result = 31 * result + (causeClassName != null ? causeClassName.hashCode() : 0);
        result = 31 * result + (causeMethodName != null ? causeMethodName.hashCode() : 0);
        result = 31 * result + (causeFileName != null ? causeFileName.hashCode() : 0);
        result = 31 * result + causeLineNum;
        result = 31 * result + (fullStacktrace != null ? fullStacktrace.hashCode() : 0);
        result = 31 * result + (customData != null ? customData.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(CrashData crashData) {
        if (crashData == null) {
            throw new NullPointerException("crashData must not be null");
        }
        return Long.valueOf(timestamp).compareTo(crashData.timestamp);
    }
}
