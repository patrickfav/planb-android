package at.favre.lib.planb.recover;

import android.content.Context;

public interface RecoverBehaviour {

    boolean killProcess();

    boolean callDefaultExceptionHandler();

    void handleCrash(Thread thread, Throwable throwable, Context context);

    PostCrashAction getPostCrashAction();

    interface PostCrashAction {
        void onUnhandledException(Thread thread, Throwable throwable, Context context);

        class Default implements PostCrashAction {
            @Override
            public void onUnhandledException(Thread thread, Throwable throwable, Context context) {

            }
        }
    }
}
