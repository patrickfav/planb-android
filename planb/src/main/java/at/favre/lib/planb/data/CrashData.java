package at.favre.lib.planb.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CrashData {

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

    public CrashData(long timestamp, String message, String throwableClassName, String threadName,
                     String causeClassName, String causeMethodName, String causeFileName, int causeLineNum,
                     String fullStacktrace, Map<String, String> customData) {
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
}
