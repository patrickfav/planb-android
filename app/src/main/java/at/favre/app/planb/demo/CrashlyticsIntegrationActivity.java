package at.favre.app.planb.demo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import at.favre.app.planb.demo.databinding.ActivityIntegrationTestBinding;
import at.favre.app.planb.demo.util.DemoAppUtil;
import at.favre.lib.planb.util.CrashUtil;
import io.fabric.sdk.android.Fabric;


public class CrashlyticsIntegrationActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, CrashlyticsIntegrationActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        ActivityIntegrationTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_integration_test);
        setSupportActionBar((Toolbar) binding.toolbar.findViewById(R.id.toolbar));

        binding.buttonActivitycrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrashUtil.crash();
            }
        });

        binding.buttonRandomcrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemoAppUtil.throwRandomRuntimeException();
            }
        });
    }
}
