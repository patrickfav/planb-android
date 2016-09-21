package at.favre.lib.planb.recover;

import android.content.Context;

public class IgnoreBehavior extends AbstractBehaviour {

    public IgnoreBehavior(PostCrashAction postCrashAction) {
        super(true, false, postCrashAction);
    }

    public IgnoreBehavior() {
        this(new PostCrashAction.Default());
    }

    @Override
    public void handleCrash(Thread thread, Throwable throwable, Context context) {
        return;
    }
}
