package at.favre.app.planb.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import at.favre.app.planb.demo.databinding.ActivityCrashDetailBinding;
import at.favre.lib.planb.PlanB;
import at.favre.lib.planb.data.CrashData;

public class CrashDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCrashDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_crash_detail);

        setSupportActionBar((Toolbar) binding.toolbar.findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CrashData cd = PlanB.get(this).getCrashDataHandler().getLatest();

        if (cd != null) {
            binding.tvStacktrace.setText(cd.fullStacktrace);
            binding.tvCause.setText(cd.throwableClassName + " caused by " + cd.causeClassName + "." + cd.causeMethodName + "(" + cd.causeFileName + ":" + cd.causeLineNum + ")");
        }
    }
}
