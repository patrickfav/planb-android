package at.favre.lib.planb.full;


import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.util.CrashDataUtil;

public abstract class ACrashDetailView extends AppCompatActivity {
    protected void setCrashDataToView(CrashData cd) {
        if (cd != null) {
            ((TextView) findViewById(R.id.title)).setText(CrashDataUtil.getClassNameForException(cd.throwableClassName));

            setVersionString("Version: ", cd.versionString + " [" + cd.applicationVariant + "]", R.id.tv_state_version);
            setVersionString("SCM: ", cd.scmString, R.id.tv_state_scm);
            setVersionString("CI: ", cd.ciString, R.id.tv_state_ci);
            setAdditionalVersionTextView(((TextView) findViewById(R.id.tv_additional_version_info)));

            final SpannableStringBuilder throwableClassName = new SpannableStringBuilder(cd.throwableClassName);
            throwableClassName.setSpan(new StyleSpan(Typeface.BOLD), 0, throwableClassName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            final SpannableStringBuilder causeFileNameAndNum = new SpannableStringBuilder(cd.causeFileName + ":" + cd.causeLineNum);
            causeFileNameAndNum.setSpan(new UnderlineSpan(), 0, causeFileNameAndNum.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            ((TextView) findViewById(R.id.timestamp)).setText(CrashDataUtil.parseDate(cd.timestamp));
            ((TextView) findViewById(R.id.tv_ex_msg)).setText(cd.message);
            ((TextView) findViewById(R.id.tv_stacktrace)).setText(cd.fullStacktrace);

            if (!cd.customData.isEmpty()) {
                findViewById(R.id.header_additional).setVisibility(View.VISIBLE);
                ViewGroup viewGroup = ((LinearLayout) findViewById(R.id.container_additional));
                viewGroup.setVisibility(View.VISIBLE);

                for (Map.Entry<String, String> entry : cd.customData.entrySet()) {
                    TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.planblib_inc_text_prop, viewGroup, false);
                    final SpannableStringBuilder key = new SpannableStringBuilder(entry.getKey()).append(": ");
                    key.setSpan(new StyleSpan(Typeface.BOLD), 0, key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv.setText(new SpannableStringBuilder(key).append(entry.getValue()));
                    viewGroup.addView(tv);
                }
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

    protected void setAdditionalVersionTextView(TextView textView) {
        textView.setVisibility(View.GONE);
    }
}
