package at.favre.lib.planb.full;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import at.favre.lib.planb.PlanB;
import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.util.CrashDataUtil;


public class CrashExplorerOverviewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    public static void start(Context context) {
        Intent starter = new Intent(context, CrashExplorerOverviewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planblib_activity_crashexplorer_overview);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar).findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new CrashDataAdapter());
        updateRecyclerView();
    }

    private void updateRecyclerView() {
        List<CrashData> list = PlanB.get().getCrashDataHandler().getAll();

        ((CrashDataAdapter) recyclerView.getAdapter()).setCrashDataList(list);
        if (list.isEmpty()) {
            findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.empty_view).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.planblib_menu_default, menu);

        tintMenuItem(menu, R.id.action_delete);
        return true;
    }

    private static void tintMenuItem(Menu menu, @IdRes int iconId) {
        MenuItem item = menu.findItem(iconId);
        Drawable icon = DrawableCompat.wrap(item.getIcon());
        DrawableCompat.setTint(icon, Color.WHITE);
        item.setIcon(icon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete All")
                    .setMessage("Do you want to delete all the saved crash data?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PlanB.get().getCrashDataHandler().clear();
                            updateRecyclerView();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        return true;
    }

    public static class CrashDataAdapter extends RecyclerView.Adapter<CrashDataHolder> {
        private List<CrashData> crashDataList;

        public CrashDataAdapter() {
        }

        public void setCrashDataList(List<CrashData> crashDataList) {
            this.crashDataList = crashDataList;
            Collections.sort(crashDataList);
            notifyDataSetChanged();
        }

        @Override
        public CrashDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CrashDataHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.planblib_inc_crashitem, parent, false));
        }

        @Override
        public void onBindViewHolder(CrashDataHolder holder, int position) {
            CrashData cd = crashDataList.get(position);

            holder.timestamp.setText(CrashDataUtil.parseDate(cd.timestamp));
            holder.exception.setText(CrashDataUtil.getClassNameForException(cd.throwableClassName));
            holder.message.setText(cd.message);
            holder.crashData = cd;
        }

        @Override
        public int getItemCount() {
            return crashDataList.size();
        }
    }

    private static class CrashDataHolder extends RecyclerView.ViewHolder {
        TextView timestamp;
        TextView exception;
        TextView message;
        CrashData crashData;

        public CrashDataHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrashExplorerDetailActivity.start(itemView.getContext());
                }
            });

            timestamp = ((TextView) itemView.findViewById(R.id.timestamp));
            exception = ((TextView) itemView.findViewById(R.id.exception));
            message = ((TextView) itemView.findViewById(R.id.message));
        }
    }
}
