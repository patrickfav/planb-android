package at.favre.lib.planb.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import at.favre.lib.planb.data.CrashData;

/**
 * Handles retrieving and persisting of crash data
 */
public interface CrashDataHandler {

    /**
     * Gets the latest crashdata and resets the internal
     * unhandled crash flag
     *
     * @return the latest crash data or null the handler is empty
     */
    @Nullable
    CrashData getLatest();

    /**
     * @return all persisted crash data
     */
    @NonNull
    List<CrashData> getAll();

    /**
     * @return currently persisted crash data
     */
    int size();

    /**
     * If {@link #persistCrashData(CrashData)} was called this will return
     * true until {@link #getLatest()} is called. Not every crash must be handled.
     * One call to {@link #getLatest()} will reset multiple new crash data.
     * <p>
     * This is for checking after the app restarts if an crash occurred.
     *
     * @return true if an crash that wasn't handled was found
     */
    boolean hasUnhandledCrash();

    /**
     * Persists a new crashdata
     *
     * @param crashData to persist
     */
    void persistCrashData(@NonNull CrashData crashData);

    /**
     * Returns the count of crash data entries that were persisted after
     * the given time.
     *
     * @param fromTimestamp timestamp from {@link java.util.Date}
     * @return count of entries
     */
    int countOfCrashesSince(long fromTimestamp);

    /**
     * Clears all persisted crash data entries.
     */
    void clear();
}
