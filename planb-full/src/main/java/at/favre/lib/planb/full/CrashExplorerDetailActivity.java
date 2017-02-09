package at.favre.lib.planb.full;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class CrashExplorerDetailActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, CrashExplorerDetailActivity.class);
        context.startActivity(starter);
    }
}
