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
        PlanB.get().init(this);
    }

    public void setPlanBSuppress() {
        PlanB.get().enableCrashHandler(
                PlanB.get().configBuilder(this)
                        .isDebugBuild(true)
                        .maxCrashReportsSaved(10)
                        .applicationVariant(BuildConfig.BUILD_TYPE, BuildConfig.FLAVOR)
                        .scm(BuildConfig.GIT_REV, BuildConfig.GIT_BRANCH)
                        .ci(BuildConfig.BUILD_NUMBER, BuildConfig.BUILD_DATE)
                        .debugBehaviour(new SuppressCrashBehaviour()).build(), this);
    }

    public void setPlanBCrashReport() {
        PlanB.get().enableCrashHandler(
                PlanB.get().configBuilder(this)
                        .isDebugBuild(true)
                        .maxCrashReportsSaved(10)
                        .applicationVariant(BuildConfig.BUILD_TYPE, BuildConfig.FLAVOR)
                        .scm(BuildConfig.GIT_REV, BuildConfig.GIT_BRANCH)
                        .ci(BuildConfig.BUILD_NUMBER, BuildConfig.BUILD_DATE)
                        .debugBehaviour(new RestartActivityBehaviour(new Intent(this, CrashDetailActivity.class))).build(), this);
    }

    public void setPlanBRestart() {
        PlanB.get().enableCrashHandler(
                PlanB.get().configBuilder(this)
                        .isDebugBuild(true)
                        .maxCrashReportsSaved(10)
                        .applicationVariant(BuildConfig.BUILD_TYPE, BuildConfig.FLAVOR)
                        .scm(BuildConfig.GIT_REV, BuildConfig.GIT_BRANCH)
                        .ci(BuildConfig.BUILD_NUMBER, BuildConfig.BUILD_DATE)
                        .debugBehaviour(new RestartActivityBehaviour()).build(), this);
    }

    public void setPlanBDefault() {
        PlanB.get().enableCrashHandler(
                PlanB.get().configBuilder(this)
                        .isDebugBuild(true)
                        .maxCrashReportsSaved(10)
                        .applicationVariant(BuildConfig.BUILD_TYPE, BuildConfig.FLAVOR)
                        .scm(BuildConfig.GIT_REV, BuildConfig.GIT_BRANCH)
                        .ci(BuildConfig.BUILD_NUMBER, BuildConfig.BUILD_DATE)
                        .debugBehaviour(new DefaultBehavior()).build(), this);
    }

    public void crash() {
        CrashUtil.crash();
    }
}
