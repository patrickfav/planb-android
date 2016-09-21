package at.favre.lib.planb.data;

import android.os.Bundle;

import java.util.Date;
import java.util.Map;

import at.favre.lib.planb.util.CrashUtil;

public class GenericCrashDataHandler {

    public final static String ARG_STACKTRACE = "ARG_STACKTRACE";
    public final static String ARG_MESSAGE = "ARG_MESSAGE";

    public Bundle createCrashDataBundle(Thread thread, Throwable throwable, Map<String, String> customData) {

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
                , customData
        );
    }
}
