package at.favre.lib.planb.full;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.parser.BugReportPlaceholderHandler;
import at.favre.lib.planb.parser.GenericMLParser;
import at.favre.lib.planb.parser.MarkdownRenderer;
import at.favre.lib.planb.recover.RestartActivityBehaviour;

public class CrashDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planb_crashdetail);

        final CrashData cd = getIntent().getParcelableExtra(RestartActivityBehaviour.KEY_CRASHDATA);

        if (cd != null) {
            ((TextView) findViewById(R.id.title)).setText(getClassName(cd.throwableClassName));

            ((TextView) findViewById(R.id.tv_state_version)).setText("Version: " + cd.versionString + " / " + cd.applicationVariant);
            ((TextView) findViewById(R.id.tv_state_scm)).setText("SCM: " + cd.scmString);
            ((TextView) findViewById(R.id.tv_state_ci)).setText("CI: " + cd.ciString);

            final SpannableStringBuilder throwableClassName = new SpannableStringBuilder(cd.throwableClassName);
            throwableClassName.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, throwableClassName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            final SpannableStringBuilder causeFileNameAndNum = new SpannableStringBuilder(cd.causeFileName + ":" + cd.causeLineNum);
            causeFileNameAndNum.setSpan(new UnderlineSpan(), 0, causeFileNameAndNum.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            ((TextView) findViewById(R.id.timestamp)).setText(new Date(cd.timestamp).toString());
            ((TextView) findViewById(R.id.tv_cause)).setText(new SpannableStringBuilder("caused by ").append(cd.causeClassName).append(".").append(cd.causeMethodName).append("(").append(causeFileNameAndNum).append(")"));

            ((TextView) findViewById(R.id.tv_stacktrace)).setText(cd.fullStacktrace);
        }
        findViewById(R.id.btn_log).setOnClickListener(new View.OnClickListener() {
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
                Log.w("muh", "==BUGREPORT START==\n\n" + parser.render(result, new MarkdownRenderer(), handler.getPlaceHolderMap()) + "\n\n==BUGREPORT END==");
                Toast.makeText(v.getContext(), "Logged to console.", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = v.getContext().getPackageManager().getLaunchIntentForPackage(v.getContext().getPackageName());
                startActivity(intent);
            }
        });
    }

    private String getClassName(String throwableClassName) {
        if (throwableClassName != null && throwableClassName.contains(".")) {
            String[] parts = throwableClassName.split(Pattern.quote("."));
            return parts[parts.length - 1];
        }
        return throwableClassName;
    }
}
