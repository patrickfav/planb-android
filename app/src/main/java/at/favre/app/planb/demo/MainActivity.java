package at.favre.app.planb.demo;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.Arrays;
import java.util.List;

import at.favre.app.planb.demo.databinding.ActivityMainBinding;
import at.favre.app.planb.demo.util.DemoAppUtil;
import at.favre.lib.planb.PlanB;
import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.full.CrashExplorerOverviewActivity;
import at.favre.lib.planb.util.CrashUtil;

public class MainActivity extends AppCompatActivity {
    private static final String OPTION_SHOW_REPORT = "show report";
    private static final String OPTION_SUPPRESS = "suppress";
    private static final String OPTION_RESTART = "restart";
    private static final String OPTION_DEFAULT = "default";

    private static final List<String> OPTIONS = Arrays.asList(OPTION_SHOW_REPORT, OPTION_SUPPRESS, OPTION_RESTART, OPTION_DEFAULT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar((Toolbar) binding.toolbar.findViewById(R.id.toolbar));
        binding.buttonCrashActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrashUtil.crash();
            }
        });

        binding.buttonCrashApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CrashApplication) getApplication()).crash();
            }
        });

        binding.buttonCrashRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemoAppUtil.throwRandomRuntimeException();
            }
        });

        binding.buttonExplorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrashExplorerOverviewActivity.start(MainActivity.this);
            }
        });

        binding.buttonHockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HockeyAppIntegrationActivity.start(MainActivity.this);
            }
        });

        binding.buttonCrashlytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrashlyticsIntegrationActivity.start(MainActivity.this);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, OPTIONS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.optionSpinner.setAdapter(adapter);
        binding.optionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CrashApplication application = ((CrashApplication) getApplication());
                String selected = OPTIONS.get(position);
                switch (selected) {
                    case OPTION_SHOW_REPORT:
                        application.setPlanBCrashReport();
                        break;
                    case OPTION_SUPPRESS:
                        application.setPlanBSuppress();
                        break;
                    case OPTION_RESTART:
                        application.setPlanBRestart();
                        break;
                    case OPTION_DEFAULT:
                        application.setPlanBDefault();
                        break;
                    default:
                        throw new IllegalArgumentException("unknown position");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.optionSpinner.setSelection(OPTIONS.indexOf(OPTION_SHOW_REPORT));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (PlanB.get().getCrashDataHandler().hasUnhandledCrash()) {
            CrashData crashData = PlanB.get().getCrashDataHandler().getLatest();
            new AlertDialog.Builder(this)
                    .setTitle("App Crash")
                    .setMessage("The following crash was recorded: " + crashData.throwableClassName)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }


}
