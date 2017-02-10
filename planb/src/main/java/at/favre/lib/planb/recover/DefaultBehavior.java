package at.favre.lib.planb.recover;

import android.content.Context;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;

public class DefaultBehavior extends AbstractBehaviour {

    public DefaultBehavior(CrashAction prePostAction, CrashAction postCrashAction) {
        super(false, true, true, prePostAction, postCrashAction);
    }

    public DefaultBehavior() {
        this(new CrashAction.Default(), new CrashAction.Default());
    }

    @Override
    public void handleCrash(Context context, Thread thread, Throwable throwable, CrashData crashData, PlanBConfig config) {
    }
}
