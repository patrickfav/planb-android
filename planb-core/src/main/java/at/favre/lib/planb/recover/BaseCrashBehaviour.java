package at.favre.lib.planb.recover;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Base implementation
 */
public abstract class BaseCrashBehaviour implements CrashRecoverBehaviour {
    private boolean killProcess;
    private boolean callDefault;
    private boolean persistCrashData;

    private CrashAction preCrashAction;
    private CrashAction postCrashAction;

    public BaseCrashBehaviour(boolean killProcess, boolean callDefault, boolean persistCrashData,
                              @Nullable CrashAction preCrashAction, @Nullable CrashAction postCrashAction) {
        this.killProcess = killProcess;
        this.callDefault = callDefault;
        this.persistCrashData = persistCrashData;
        this.preCrashAction = preCrashAction == null ? new CrashAction.Noop() : preCrashAction;
        this.postCrashAction = postCrashAction == null ? new CrashAction.Noop() : postCrashAction;
    }

    @Override
    public boolean killProcess() {
        return killProcess;
    }

    @Override
    public boolean callDefaultExceptionHandler() {
        return callDefault;
    }

    @Override
    public boolean persistCrashData() {
        return persistCrashData;
    }

    @NonNull
    @Override
    public CrashAction getPreCrashAction() {
        return preCrashAction;
    }

    @NonNull
    @Override
    public CrashAction getPostCrashAction() {
        return postCrashAction;
    }

}
