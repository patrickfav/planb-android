package at.favre.lib.planb.data;

import android.os.Bundle;
import android.os.Parcel;

import java.util.Date;
import java.util.Map;

import at.favre.lib.planb.util.CrashUtil;

public class GenericCrashDataHandler {

    public final static String ARG_CRASHDATA = "ARG_CRASHDATA";

    public String serialize(CrashData crashData) {
        return null;
    }

    public Bundle createCrashDataBundle(Thread thread, Throwable throwable, Map<String, String> customData) {
        Bundle b = new Bundle();
        b.putParcelable(ARG_CRASHDATA,null);
        return b;
    }

    public static CrashData createFromCrash(Thread thread, Throwable throwable, Map<String, String> customData) {
        if(throwable == null) {

        }
        StackTraceElement element = throwable.getStackTrace()[3];

        return new CrashData(
                new Date().getTime(),
                throwable.getMessage(),
                throwable.getClass().getName(),
                thread.getName(),
                element.getClassName(),
                element.getMethodName(),
                element.getFileName(),
                element.getLineNumber(),
                CrashUtil.printStacktrace(throwable),
                customData
        );
    }
}
