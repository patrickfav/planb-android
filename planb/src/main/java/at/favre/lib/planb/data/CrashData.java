package at.favre.lib.planb.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the model that contains all the crash information
 */
public class CrashData implements Comparable<CrashData>, Parcelable {
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
    private static final String ARG_VERSION = "ARG_VERSION";
    private static final String ARG_SCM = "ARG_SCM";
    private static final String ARG_CI = "ARG_CI";
    private static final String ARG_APP_VARIANT = "ARG_APP_VARIANT";
    private static final String ARG_CUSTOM_MAP = "ARG_CUSTOM_MAP";

    /**
     * Synthetic unique id
     */
    public final String id;

    /**
     * Timestamp as provide by {@link java.util.Date}
     */
    public final long timestamp;

    /**
     * Message of the {@link Throwable}
     */
    public final String message;

    /**
     * Full class name of the {@link Throwable}
     */
    public final String throwableClassName;

    /**
     * Name of the thread
     */
    public final String threadName;

    /**
     * Class name of the first cause
     */
    public final String causeClassName;

    /**
     * Method name of the first cause
     */
    public final String causeMethodName;

    /**
     * File name of the first cause
     */
    public final String causeFileName;

    /**
     * File line number of the first cause
     */
    public final int causeLineNum;

    /**
     * Full stacktrace
     */
    public final String fullStacktrace;

    /**
     * String containing version, versionCode etc.
     */
    public final String versionString;

    /**
     * Built type and variant
     */
    public final String applicationVariant;

    /**
     * SCM revision hashes, branches etc.
     */
    public final String scmString;

    /**
     * Continous Integration build number, etc.
     */
    public final String ciString;

    public Map<String, String> customData;

    public CrashData(String id, long timestamp, String message, String throwableClassName, String threadName,
                     String causeClassName, String causeMethodName, String causeFileName, int causeLineNum,
                     String fullStacktrace, String versionString, String applicationVariant, String scmString,
                     String ciString, Map<String, String> customData) {
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
        this.versionString = versionString;
        this.applicationVariant = applicationVariant;
        this.scmString = scmString;
        this.ciString = ciString;
    }

    public static CrashData create(Map<String, String> map) {
        Map<String, String> customData = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().startsWith(ARG_CUSTOM_MAP)) {
                String key = entry.getKey().substring(entry.getKey().indexOf(MAP_SPLIT.charAt(0)) + 1, entry.getKey().length());
                customData.put(key, entry.getValue());
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
                map.get(ARG_VERSION),
                map.get(ARG_APP_VARIANT),
                map.get(ARG_SCM),
                map.get(ARG_CI),
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
        map.put(ARG_VERSION, versionString);
        map.put(ARG_APP_VARIANT, applicationVariant);
        map.put(ARG_SCM, scmString);
        map.put(ARG_CI, ciString);

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
    public int compareTo(@NonNull CrashData crashData) {
        return Long.valueOf(crashData.timestamp).compareTo(timestamp);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.timestamp);
        dest.writeString(this.message);
        dest.writeString(this.throwableClassName);
        dest.writeString(this.threadName);
        dest.writeString(this.causeClassName);
        dest.writeString(this.causeMethodName);
        dest.writeString(this.causeFileName);
        dest.writeInt(this.causeLineNum);
        dest.writeString(this.fullStacktrace);
        dest.writeString(this.versionString);
        dest.writeString(this.applicationVariant);
        dest.writeString(this.scmString);
        dest.writeString(this.ciString);
        dest.writeInt(this.customData.size());
        for (Map.Entry<String, String> entry : this.customData.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    protected CrashData(Parcel in) {
        this.id = in.readString();
        this.timestamp = in.readLong();
        this.message = in.readString();
        this.throwableClassName = in.readString();
        this.threadName = in.readString();
        this.causeClassName = in.readString();
        this.causeMethodName = in.readString();
        this.causeFileName = in.readString();
        this.causeLineNum = in.readInt();
        this.fullStacktrace = in.readString();
        this.versionString = in.readString();
        this.applicationVariant = in.readString();
        this.scmString = in.readString();
        this.ciString = in.readString();
        int customDataSize = in.readInt();
        this.customData = new HashMap<String, String>(customDataSize);
        for (int i = 0; i < customDataSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.customData.put(key, value);
        }
    }

    public static final Parcelable.Creator<CrashData> CREATOR = new Parcelable.Creator<CrashData>() {
        @Override
        public CrashData createFromParcel(Parcel source) {
            return new CrashData(source);
        }

        @Override
        public CrashData[] newArray(int size) {
            return new CrashData[size];
        }
    };
}
