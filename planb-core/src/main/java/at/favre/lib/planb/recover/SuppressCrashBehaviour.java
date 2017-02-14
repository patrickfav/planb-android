package at.favre.lib.planb.recover;

import android.content.Context;
import android.support.annotation.NonNull;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;

/**
 * Crash behaviour that suppresses any UI showing an error. App will just disappear when crashing.
 * Crash data will still be persisted.
 */
public class SuppressCrashBehaviour extends BaseCrashBehaviour {

    public SuppressCrashBehaviour(CrashAction prePostAction, CrashAction postCrashAction) {
        super(true, false, true, prePostAction, postCrashAction);
    }

    public SuppressCrashBehaviour() {
        this(new CrashAction.Noop(), new CrashAction.Noop());
    }

    @Override
    public void handleCrash(@NonNull Context context, @NonNull Thread thread, @NonNull Throwable throwable, @NonNull CrashData crashData, @NonNull PlanBConfig config) {
        //no-op
    }
}
