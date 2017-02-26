package at.favre.lib.planb.interfaces;

import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Use this interface for any exception to provide additional debugging
 * data the the planb exception handler.
 */
public interface ICrashExceptionData {

    /**
     * Custom generic data to persist in {@link at.favre.lib.planb.data.CrashData}
     * when handling an exception.
     *
     * @return a non null map
     */
    @NonNull
    Map<String,String> getAdditionalExceptionData();
}
