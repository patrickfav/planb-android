package at.favre.app.planb.demo;

import android.app.Application;
import android.content.Intent;

import at.favre.lib.planb.PlanB;
import at.favre.lib.planb.recover.RestartActivityBehaviour;
import at.favre.lib.planb.util.CrashUtil;

public class CrashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PlanB.get(this).enableCrashHandler(
                PlanB.get(this).configBuilder().debugMode(true)
                        .debugCrashBehaviour(new RestartActivityBehaviour(new Intent(this,CrashDetailActivity.class))).build());
    }

    public void crash() {
        CrashUtil.crash();
    }
}
