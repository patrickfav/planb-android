package at.favre.lib.planb.recover;

public abstract class AbstractBehaviour implements RecoverBehaviour {
    private boolean killProcess;
    private boolean callDefault;
    private boolean persistCrashData;

    private CrashAction preCrashAction;
    private CrashAction postCrashAction;

    public AbstractBehaviour(boolean killProcess, boolean callDefault, boolean persistCrashData,
                             CrashAction preCrashAction, CrashAction postCrashAction) {
        this.killProcess = killProcess;
        this.callDefault = callDefault;
        this.persistCrashData = persistCrashData;
        this.preCrashAction = preCrashAction == null ? new CrashAction.Default() : preCrashAction;
        this.postCrashAction = postCrashAction == null ? new CrashAction.Default() : postCrashAction;
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

    @Override
    public CrashAction getPreCrashAction() {
        return preCrashAction;
    }

    @Override
    public CrashAction getPostCrashAction() {
        return postCrashAction;
    }

}
