package at.favre.app.planb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import at.favre.lib.planb.PlanB;
import at.favre.lib.planb.data.CrashData;

/**
 * Created by PatrickF on 23.09.2016.
 */

public class CrashDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crash_detail);

        CrashData cd = PlanB.get(this).getCrashDataHandler().getLatest();

        if (cd != null) {
            ((TextView) findViewById(R.id.tv_stacktrace)).setText(cd.fullStacktrace);
            ((TextView) findViewById(R.id.tv_cause)).setText(cd.throwableClassName+" caused by "+cd.causeClassName+"."+cd.causeMethodName+"("+cd.causeFileName+":"+cd.causeLineNum+")");

        }
    }
}
