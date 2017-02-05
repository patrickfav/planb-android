package at.favre.app.planb.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import java.util.regex.Pattern;

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
            binding.title.setText(getClassName(cd.throwableClassName));

            final SpannableStringBuilder throwableClassName = new SpannableStringBuilder(cd.throwableClassName);
            throwableClassName.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, throwableClassName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            final SpannableStringBuilder causeFileNameAndNum = new SpannableStringBuilder(cd.causeFileName + ":" + cd.causeLineNum);
            causeFileNameAndNum.setSpan(new UnderlineSpan(), 0, causeFileNameAndNum.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            binding.tvExceptionClassName.setText(throwableClassName);
            binding.tvCause.setText(new SpannableStringBuilder("caused by ").append(cd.causeClassName).append(".").append(cd.causeMethodName).append("(").append(causeFileNameAndNum).append(")"));

            binding.tvStacktrace.setText(cd.fullStacktrace);
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
