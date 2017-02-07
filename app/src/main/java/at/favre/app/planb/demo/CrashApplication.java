package at.favre.app.planb.demo;

import android.app.Application;
import android.content.Intent;

import at.favre.lib.planb.PlanB;
import at.favre.lib.planb.full.CrashDetailActivity;
import at.favre.lib.planb.recover.DefaultBehavior;
import at.favre.lib.planb.recover.RestartActivityBehaviour;
import at.favre.lib.planb.recover.SuppressCrashBehaviour;
import at.favre.lib.planb.util.CrashUtil;

public class CrashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setPlanBSuppress() {
        PlanB.get(this).enableCrashHandler(
                PlanB.get(this).configBuilder().isDebugBuild(true).debugCrashBehaviour(new SuppressCrashBehaviour()).build());
    }

    public void setPlanBCrashReport() {
        PlanB.get(this).enableCrashHandler(
                PlanB.get(this).configBuilder().isDebugBuild(true)
                        .debugCrashBehaviour(new RestartActivityBehaviour(new Intent(this, CrashDetailActivity.class))).build());
    }

    public void setPlanBRestart() {
        PlanB.get(this).enableCrashHandler(
                PlanB.get(this).configBuilder().isDebugBuild(true).debugCrashBehaviour(new RestartActivityBehaviour()).build());
    }

    public void setPlanBDefault() {
        PlanB.get(this).enableCrashHandler(
                PlanB.get(this).configBuilder().isDebugBuild(true).debugCrashBehaviour(new DefaultBehavior()).build());
    }

    public void crash() {
        CrashUtil.crash();
    }
}
