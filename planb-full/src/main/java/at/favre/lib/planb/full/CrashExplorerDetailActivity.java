package at.favre.lib.planb.full;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.full.util.ViewUtil;
import at.favre.lib.planb.util.CrashDataUtil;

public class CrashExplorerDetailActivity extends ACrashDetailView {
    private static final String TAG = CrashExplorerDetailActivity.class.getName();
    private static final String KEY_CD = "CD";

    private CrashData crashData;

    public static void start(Context context, CrashData crashData) {
        Intent starter = new Intent(context, CrashExplorerDetailActivity.class);
        starter.putExtra(KEY_CD, crashData);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planblib_activity_crashexplorer_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar).findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        crashData = getIntent().getParcelableExtra(KEY_CD);
        setCrashDataToView(crashData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.planblib_menu_detail, menu);
        ViewUtil.tintMenuItem(menu, R.id.action_log, Color.WHITE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_log) {
            Log.w(TAG, CrashDataUtil.getLogString(crashData).toString());
            Toast.makeText(this, R.string.crashexplorer_toast_log, Toast.LENGTH_SHORT).show();
        } else if (i == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
