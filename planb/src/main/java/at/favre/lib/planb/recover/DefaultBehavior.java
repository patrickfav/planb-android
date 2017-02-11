package at.favre.lib.planb.recover;

import android.content.Context;
import android.support.annotation.NonNull;

import at.favre.lib.planb.PlanBConfig;
import at.favre.lib.planb.data.CrashData;

/**
 * Android's default crash handling behavior (ie. just showing a dialog)
 */
public class DefaultBehavior extends BaseCrashBehaviour {

    public DefaultBehavior(CrashAction prePostAction, CrashAction postCrashAction) {
        super(false, true, true, prePostAction, postCrashAction);
    }

    public DefaultBehavior() {
        this(new CrashAction.Noop(), new CrashAction.Noop());
    }

    @Override
    public void handleCrash(@NonNull Context context, @NonNull Thread thread, @NonNull Throwable throwable, @NonNull CrashData crashData, @NonNull PlanBConfig config) {
    }
}
