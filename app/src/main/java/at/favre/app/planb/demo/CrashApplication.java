package at.favre.app.planb.demo;

import android.app.Application;
import android.content.Intent;

import at.favre.lib.planb.PlanB;
import at.favre.lib.planb.full.CrashDetailActivity;
import at.favre.lib.planb.util.CrashUtil;

public class CrashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PlanB.get().init(true, PlanB.newConfig(this)
                .applicationVariant(BuildConfig.BUILD_TYPE, BuildConfig.FLAVOR)
                .scm(BuildConfig.GIT_REV, BuildConfig.GIT_BRANCH)
                .ci(BuildConfig.BUILD_NUMBER, BuildConfig.BUILD_DATE).build());
        setPlanBCrashReport();
    }

    public void setPlanBSuppress() {
        PlanB.get().enableCrashHandler(this, PlanB.behaviourFactory().createSuppressCrashBehaviour());
    }

    public void setPlanBCrashReport() {
        PlanB.get().enableCrashHandler(this, PlanB.behaviourFactory().createStartActivityCrashBehaviour(new Intent(this, CrashDetailActivity.class)));
    }

    public void setPlanBRestart() {
        PlanB.get().enableCrashHandler(this, PlanB.behaviourFactory().createRestartForegroundActivityCrashBehaviour());
    }

    public void setPlanBDefault() {
        PlanB.get().enableCrashHandler(this, PlanB.behaviourFactory().createDefaultHandlerBehaviour());
    }

    public void disableCrashHandling() {
        PlanB.get().disableCrashHandler();
    }

    public void crash() {
        CrashUtil.crash();
    }
}
