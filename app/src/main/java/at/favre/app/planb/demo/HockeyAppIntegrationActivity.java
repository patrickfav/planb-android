package at.favre.app.planb.demo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import at.favre.app.planb.demo.databinding.ActivityIntegrationTestBinding;
import at.favre.app.planb.demo.util.DemoAppUtil;
import at.favre.lib.planb.util.CrashUtil;


public class HockeyAppIntegrationActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, HockeyAppIntegrationActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkForUpdates();
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

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        if (BuildConfig.DEBUG) {
            UpdateManager.register(this);
        }
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }
}
