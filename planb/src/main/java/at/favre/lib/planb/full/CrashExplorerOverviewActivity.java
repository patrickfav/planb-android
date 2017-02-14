package at.favre.lib.planb.full;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import at.favre.lib.planb.PlanB;
import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.full.util.CrashDataExceptionComparator;
import at.favre.lib.planb.full.util.ViewUtil;
import at.favre.lib.planb.util.CrashDataUtil;


public class CrashExplorerOverviewActivity extends AppCompatActivity {
    private static final String TAG = CrashExplorerOverviewActivity.class.getName();
    private static final String KEY_SORT = "SORT";

    private final static int SORT_DATE = 0;
    private final static int SORT_EXCEPTION_NAME = 1;

    private RecyclerView recyclerView;
    private List<CrashData> crashDataList;
    private int currentSort = SORT_DATE;

    public static void start(Context context) {
        Intent starter = new Intent(context, CrashExplorerOverviewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentSort = savedInstanceState.getInt(KEY_SORT, SORT_DATE);
        }

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
        crashDataList = PlanB.get().getCrashDataHandler().getAll();

        ((CrashDataAdapter) recyclerView.getAdapter()).setCrashDataList(crashDataList, currentSort);
        if (crashDataList.isEmpty()) {
            findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.empty_view).setVisibility(View.GONE);
        }
        getSupportActionBar().setTitle(getTitle() + " (" + crashDataList.size() + ")");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.planblib_menu_overview, menu);

        ViewUtil.tintMenuItem(menu, R.id.action_delete, Color.WHITE);
        ViewUtil.tintMenuItem(menu, R.id.action_log, Color.WHITE);
        ViewUtil.tintMenuItem(menu, R.id.action_sort, Color.WHITE);
        return true;
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
        } else if (i == R.id.action_log) {
            Log.w(TAG, CrashDataUtil.getLogString(crashDataList).toString());
            Toast.makeText(this, R.string.crashexplorer_toast_log, Toast.LENGTH_SHORT).show();
        } else if (i == R.id.action_sort_date || i == R.id.action_sort_exception) {
            if (i == R.id.action_sort_date) {
                currentSort = SORT_DATE;
            }
            if (i == R.id.action_sort_exception) {
                currentSort = SORT_EXCEPTION_NAME;
            }
            updateRecyclerView();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SORT, currentSort);
    }

    public static class CrashDataAdapter extends RecyclerView.Adapter<CrashDataHolder> {
        private List<CrashData> crashDataList;

        public CrashDataAdapter() {
        }

        public void setCrashDataList(List<CrashData> crashDataList, int sortType) {
            this.crashDataList = crashDataList;

            if (sortType == SORT_EXCEPTION_NAME) {
                Collections.sort(crashDataList, new CrashDataExceptionComparator());
            } else {
                Collections.sort(crashDataList);
            }
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
                    CrashExplorerDetailActivity.start(itemView.getContext(), crashData);
                }
            });

            timestamp = ((TextView) itemView.findViewById(R.id.timestamp));
            exception = ((TextView) itemView.findViewById(R.id.exception));
            message = ((TextView) itemView.findViewById(R.id.message));
        }
    }
}
