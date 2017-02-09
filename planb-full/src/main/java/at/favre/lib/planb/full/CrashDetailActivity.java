package at.favre.lib.planb.full;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.parser.BugReportPlaceholderHandler;
import at.favre.lib.planb.parser.GenericMLParser;
import at.favre.lib.planb.parser.MarkdownRenderer;
import at.favre.lib.planb.recover.RestartActivityBehaviour;
import at.favre.lib.planb.util.CrashDataUtil;

public class CrashDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planblib_activity_crashdetail);

        vibrate(savedInstanceState);

        final CrashData cd = getIntent().getParcelableExtra(RestartActivityBehaviour.KEY_CRASHDATA);

        if (cd != null) {
            ((TextView) findViewById(R.id.title)).setText(CrashDataUtil.getClassNameForException(cd.throwableClassName));

            setVersionString("Version: ", cd.versionString + " [" + cd.applicationVariant + "]", R.id.tv_state_version);
            setVersionString("SCM: ", cd.scmString, R.id.tv_state_scm);
            setVersionString("CI: ", cd.ciString, R.id.tv_state_ci);
            setAdditionalVersionTextView(((TextView) findViewById(R.id.tv_additional_version_info)));

            final SpannableStringBuilder throwableClassName = new SpannableStringBuilder(cd.throwableClassName);
            throwableClassName.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, throwableClassName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            final SpannableStringBuilder causeFileNameAndNum = new SpannableStringBuilder(cd.causeFileName + ":" + cd.causeLineNum);
            causeFileNameAndNum.setSpan(new UnderlineSpan(), 0, causeFileNameAndNum.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            ((TextView) findViewById(R.id.timestamp)).setText(CrashDataUtil.parseDate(cd.timestamp));
            ((TextView) findViewById(R.id.tv_ex_msg)).setText(cd.message);
            ((TextView) findViewById(R.id.tv_stacktrace)).setText(cd.fullStacktrace);
        }

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

    private void setVersionString(String label, String content, @IdRes int id) {
        if (content != null && !content.isEmpty()) {
            final SpannableStringBuilder titleVersion = new SpannableStringBuilder(label);
            titleVersion.setSpan(new StyleSpan(Typeface.BOLD), 0, titleVersion.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((TextView) findViewById(id)).setText(new SpannableStringBuilder(titleVersion).append(content));
        } else {
            findViewById(id).setVisibility(View.GONE);
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
                GenericMLParser parser = new GenericMLParser();
                Log.w("", "==BUGREPORT START==\n\n" + parser.render(result, new MarkdownRenderer(), handler.getPlaceHolderMap()) + "\n\n==BUGREPORT END==");
                Toast.makeText(v.getContext(), "Logged to console.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void configureSecondaryButton(final CrashData cd, Button button) {
        button.setText(R.string.crashdetail_btn_secondary);
        findViewById(R.id.btn_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = v.getContext().getPackageManager().getLaunchIntentForPackage(v.getContext().getPackageName());
                startActivity(intent);
            }
        });
    }

    protected void setAdditionalVersionTextView(TextView textView) {
        textView.setVisibility(View.GONE);
    }
}
