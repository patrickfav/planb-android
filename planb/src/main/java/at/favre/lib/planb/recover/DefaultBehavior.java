package at.favre.lib.planb.recover;

import android.content.Context;

public class DefaultBehavior extends AbstractBehaviour {

    public DefaultBehavior(PostCrashAction postCrashAction) {
        super(false, true, postCrashAction);
    }

    public DefaultBehavior() {
        this(new RecoverBehaviour.PostCrashAction.Default());
    }

    @Override
    public void handleCrash(Thread thread, Throwable throwable, Context context) {
        return;
    }
}
