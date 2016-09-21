package at.favre.app.planb;

import android.app.Application;
import android.content.Intent;

import at.favre.lib.planb.PlanBUncaughtExceptionHandler;
import at.favre.lib.planb.util.CrashUtil;

public class CrashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(
                new PlanBUncaughtExceptionHandler(new Intent(this, MainActivity.class), getApplicationContext()));
    }

    public void crash() {
        CrashUtil.crash();
    }
}
