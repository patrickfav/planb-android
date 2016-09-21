package at.favre.lib.planb.recover;

public abstract class AbstractBehaviour implements RecoverBehaviour {
    private boolean killProcess;
    private boolean callDefault;
    private PostCrashAction postCrashAction;

    public AbstractBehaviour(boolean killProcess, boolean callDefault,
                             PostCrashAction postCrashAction) {
        this.killProcess = killProcess;
        this.callDefault = callDefault;
        this.postCrashAction = postCrashAction;
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
    public PostCrashAction getPostCrashAction() {
        return postCrashAction;
    }
}
