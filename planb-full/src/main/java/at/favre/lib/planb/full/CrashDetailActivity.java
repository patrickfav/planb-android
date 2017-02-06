package at.favre.lib.planb.full;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import java.util.Date;
import java.util.regex.Pattern;

import at.favre.lib.planb.PlanB;
import at.favre.lib.planb.data.CrashData;

public class CrashDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planb_crashdetail);

        CrashData cd = PlanB.get(this).getCrashDataHandler().getLatest();

        if (cd != null) {
            ((TextView) findViewById(R.id.title)).setText(getClassName(cd.throwableClassName));

            final SpannableStringBuilder throwableClassName = new SpannableStringBuilder(cd.throwableClassName);
            throwableClassName.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, throwableClassName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            final SpannableStringBuilder causeFileNameAndNum = new SpannableStringBuilder(cd.causeFileName + ":" + cd.causeLineNum);
            causeFileNameAndNum.setSpan(new UnderlineSpan(), 0, causeFileNameAndNum.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            ((TextView) findViewById(R.id.timestamp)).setText(new Date(cd.timestamp).toString());
            ((TextView) findViewById(R.id.tv_cause)).setText(new SpannableStringBuilder("caused by ").append(cd.causeClassName).append(".").append(cd.causeMethodName).append("(").append(causeFileNameAndNum).append(")"));

            ((TextView) findViewById(R.id.tv_stacktrace)).setText(cd.fullStacktrace);
        }
    }

    private String getClassName(String throwableClassName) {
        if (throwableClassName != null && throwableClassName.contains(".")) {
            String[] parts = throwableClassName.split(Pattern.quote("."));
            return parts[parts.length - 1];
        }
        return throwableClassName;
    }
}
