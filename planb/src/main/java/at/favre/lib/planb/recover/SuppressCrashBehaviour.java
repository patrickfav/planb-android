package at.favre.lib.planb.recover;

import android.content.Context;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;

public class SuppressCrashBehaviour extends AbstractBehaviour {

    public SuppressCrashBehaviour(CrashAction prePostAction, CrashAction postCrashAction) {
        super(true, false, true, prePostAction, postCrashAction);
    }

    public SuppressCrashBehaviour() {
        this(new CrashAction.Default(), new CrashAction.Default());
    }

    @Override
    public void handleCrash(Context context, Thread thread, Throwable throwable, CrashData crashData, PlanBConfig config) {
        //no-op
    }
}
