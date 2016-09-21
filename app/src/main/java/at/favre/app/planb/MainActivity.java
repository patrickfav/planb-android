package at.favre.app.planb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import at.favre.lib.planb.util.CrashUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrashUtil.crash();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CrashApplication) getApplication()).crash();
            }
        });
    }
}
