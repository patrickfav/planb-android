package at.favre.lib.planb;


import android.content.Context;
import android.content.Intent;

public class PlanBUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    private Intent intent;
    private Context context;

    public PlanBUncaughtExceptionHandler(Intent intent, Context context) {
        this.intent = intent;
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        context.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
