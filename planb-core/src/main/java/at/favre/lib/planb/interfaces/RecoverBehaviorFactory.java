package at.favre.lib.planb.interfaces;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;

/**
 * Factory for all built-in crash behaviors
 */
public interface RecoverBehaviorFactory {

    /**
     * Creates behaviour where crashes will be suppressed and no error dialog or similar will be shown
     */
    CrashRecoverBehaviour createSuppressCrashBehaviour();

    /**
     * Creates behaviour where crashes will be suppressed and no error dialog or similar will be shown
     *
     * @param prePostAction   called before {@link CrashRecoverBehaviour#handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     * @param postCrashAction called after {@link CrashRecoverBehaviour#handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     */
    CrashRecoverBehaviour createSuppressCrashBehaviour(@Nullable CrashRecoverBehaviour.CrashAction prePostAction, @Nullable CrashRecoverBehaviour.CrashAction postCrashAction);

    /**
     * Creates behaviour which acts like the default handle (ie. showing a OS error dialog)
     */
    CrashRecoverBehaviour createDefaultHandlerBehaviour();

    /**
     * Creates behaviour which acts like the default handle (ie. showing a OS error dialog)
     *
     * @param prePostAction   called before {@link CrashRecoverBehaviour#handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     * @param postCrashAction called after {@link CrashRecoverBehaviour#handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     */
    CrashRecoverBehaviour createDefaultHandlerBehaviour(@Nullable CrashRecoverBehaviour.CrashAction prePostAction, @Nullable CrashRecoverBehaviour.CrashAction postCrashAction);

    /**
     * Creates behaviour where the current foreground activity will be started on crash.
     * Note that the activity might miss some bundle data, since it will be restarted with an empty bundle.
     * See {@link #createRestartLauncherActivityCrashBehaviour()} for a more stable approach.
     */
    CrashRecoverBehaviour createRestartForegroundActivityCrashBehaviour();

    /**
     * Creates behaviour where the current launcher activity will be started on crash, effectively restarting the app.
     */
    CrashRecoverBehaviour createRestartLauncherActivityCrashBehaviour();

    /**
     * Creates behaviour where the current launcher activity will be started on crash, effectively restarting the app.
     *
     * @param prePostAction   called before {@link CrashRecoverBehaviour#handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     * @param postCrashAction called after {@link CrashRecoverBehaviour#handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     */
    CrashRecoverBehaviour createRestartLauncherActivityCrashBehaviour(@Nullable CrashRecoverBehaviour.CrashAction prePostAction, @Nullable CrashRecoverBehaviour.CrashAction postCrashAction);

    /**
     * Creates behaviour where the provided activity will be started
     *
     * @param intent of the activity to be started
     */
    CrashRecoverBehaviour createStartActivityCrashBehaviour(Intent intent);

    /**
     * Creates behaviour where the current foreground activity will be started on crash.
     * Note that the activity might miss some bundle data, since it will be restarted with an empty bundle.
     * See {@link #createRestartLauncherActivityCrashBehaviour()} for a more stable approach.
     *
     * @param prePostAction   called before {@link CrashRecoverBehaviour#handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     * @param postCrashAction called after {@link CrashRecoverBehaviour#handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     */
    CrashRecoverBehaviour createRestartForegroundActivityCrashBehaviour(@Nullable CrashRecoverBehaviour.CrashAction prePostAction, @Nullable CrashRecoverBehaviour.CrashAction postCrashAction);

    /**
     * Creates behaviour where the provided activity will be started.
     *
     * @param intent          of the activity to be started
     * @param prePostAction   called before {@link CrashRecoverBehaviour#handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     * @param postCrashAction called after {@link CrashRecoverBehaviour#handleCrash(Context, Thread, Throwable, CrashData, PlanBConfig)}
     */
    CrashRecoverBehaviour createStartActivityCrashBehaviour(Intent intent, @Nullable CrashRecoverBehaviour.CrashAction prePostAction, @Nullable CrashRecoverBehaviour.CrashAction postCrashAction);
}
