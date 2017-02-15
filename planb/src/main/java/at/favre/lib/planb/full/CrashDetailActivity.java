package at.favre.lib.planb.full;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.parser.BugReportPlaceholderHandler;
import at.favre.lib.planb.parser.GenericMLParser;
import at.favre.lib.planb.parser.MarkupRenderer;
import at.favre.lib.planb.recover.StartActivityBehaviour;

public class CrashDetailActivity extends ACrashDetailView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planblib_activity_crashdetail);

        vibrate(savedInstanceState);

        final CrashData cd = getIntent().getParcelableExtra(StartActivityBehaviour.KEY_CRASHDATA);
        setCrashDataToView(cd);

        configurePrimaryButton(cd, ((Button) findViewById(R.id.btn_log)));
        configureSecondaryButton(cd, ((Button) findViewById(R.id.btn_restart)));
    }

    protected void vibrate(Bundle savedInstanceState) {
        if (savedInstanceState == null && ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(200);
            }
        }
    }

    protected void configurePrimaryButton(final CrashData cd, Button button) {
        button.setText(R.string.crashdetail_btn_primary);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BugReportPlaceholderHandler handler = new BugReportPlaceholderHandler(cd);

                InputStream inputStream = getResources().openRawResource(R.raw.bugreport);
                Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int syntaxId = getIntent().getIntExtra(StartActivityBehaviour.KEY_BUGREPORT_SYNTAX, MarkupRenderer.ML_MARKDOWN);

                GenericMLParser parser = new GenericMLParser();
                //noinspection WrongConstant
                Log.w("", "==BUGREPORT START==\n\n" + parser.render(result, MarkupRenderer.Util.getById(syntaxId), handler.getPlaceHolderMap()) + "\n\n==BUGREPORT END==");
                Toast.makeText(v.getContext(), R.string.crashexplorer_toast_log, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void configureSecondaryButton(final CrashData cd, Button button) {
        button.setText(R.string.crashdetail_btn_secondary);
        findViewById(R.id.btn_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = v.getContext().getPackageManager().getLaunchIntentForPackage(v.getContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
