package at.favre.lib.planb;

import android.content.Context;

import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.interfaces.CrashRecoverBehaviour;


public class MockCrashAction implements CrashRecoverBehaviour.CrashAction {
    private int callCounter = 0;

    @Override
    public void onUnhandledException(Context context, Thread thread, Throwable throwable, CrashData crashData, PlanBConfig config) {
        callCounter++;
    }

    public int getCallCounter() {
        return callCounter;
    }
}
