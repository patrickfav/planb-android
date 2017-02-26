package at.favre.lib.planb;

import android.content.Context;

import at.favre.lib.planb.data.CrashDataHandler;

/**
 * Factory for built-in crash data handler
 */
public interface CrashDataHandlerFactory {

    /**
     * Creates the default {@link android.content.SharedPreferences} backed crash storage. Uses
     * default capacity of 50
     *
     * @param ctx
     * @return the crash data handler
     */
    CrashDataHandler createSharedPrefHandler(Context ctx);

    /**
     * Creates the default {@link android.content.SharedPreferences} backed crash storage
     *
     * @param ctx
     * @param maxCapacity max amount of saved crashes (max is 200)
     * @return the crash data handler
     */
    CrashDataHandler createSharedPrefHandler(Context ctx, int maxCapacity);

    /**
     * Creates an in-memory handler, useful for e.g. production
     *
     * @return the crash data handler
     */
    CrashDataHandler createInMemoryHandler();
}
