package at.favre.lib.planb.recover;

import android.content.Context;
import android.support.annotation.NonNull;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;

/**
 * Interface for the recovery behaviour. Will define what exactly happens
 * when an unhandled crash was encountered.
 * <p>
 * Basic process like persisting the crash data and killing the process will be done
 * within the framework and configured with this interface.
 * <p>
 * Use the pre and post handler to easily modify ready to use implementations.
 */
public interface CrashRecoverBehaviour {

    /**
     * If the process should be killed after handling the crash.
     * This is activated in most cases.
     * @return if the process should be killed after handling
     */
    boolean killProcess();

    /**
     * If the default exception handler should be called. Android's default
     * handler will just show a dialog. However if you use other crash frameworks
     * you may want to call the default.
     *
     * @return if the default exception handler should be called
     */
    boolean callDefaultExceptionHandler();

    /**
     * If the crash data should be persisted with the {@link at.favre.lib.planb.data.CrashDataHandler}
     * @return true if should be persisted
     */
    boolean persistCrashData();

    /**
     * The main crash handle call. Implement with your own custom behaviour.
     *
     * @param context
     * @param thread    of the crash
     * @param throwable the crashes's throwable class
     * @param crashData the crash data of this crash
     * @param config    current config of the lib
     */
    void handleCrash(@NonNull Context context, @NonNull Thread thread, @NonNull Throwable throwable, @NonNull CrashData crashData, @NonNull PlanBConfig config);

    /**
     * Set an action before the {@link #handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     * @return the action, must not be null
     */
    @NonNull
    CrashAction getPreCrashAction();

    /**
     * Set an action before the {@link #handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     * @return the action, must not be null
     */
    @NonNull
    CrashAction getPostCrashAction();

    /**
     * A crash action used with {@link #getPreCrashAction()} and {@link #getPostCrashAction()}
     */
    interface CrashAction {
        /**
         * Handling the crash event
         * @param context
         * @param thread of the crash
         * @param throwable the crashes's throwable class
         * @param crashData the crash data of this crash
         * @param config current config of the lib
         */
        void onUnhandledException(Context context, Thread thread, Throwable throwable, CrashData crashData, PlanBConfig config);

        /**
         * Default no-op implementation
         */
        class Noop implements CrashAction {
            @Override
            public void onUnhandledException(Context context, Thread thread, Throwable throwable, CrashData crashData, PlanBConfig config) {

            }
        }
    }
}
