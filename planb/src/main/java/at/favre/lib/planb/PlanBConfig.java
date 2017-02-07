package at.favre.lib.planb;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import at.favre.lib.planb.data.CrashDataHandler;
import at.favre.lib.planb.data.SharedPrefStorage;
import at.favre.lib.planb.recover.DefaultBehavior;
import at.favre.lib.planb.recover.RecoverBehaviour;

public class PlanBConfig {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ML_MARKDOWN, ML_TEXTILE, ML_ASCIIDOC, ML_HTML})
    public @interface MarkupLanguage {
    }

    public static final int ML_MARKDOWN = 0;
    public static final int ML_TEXTILE = 1;
    public static final int ML_ASCIIDOC = 2;
    public static final int ML_HTML = 3;

    public final boolean isDebugBuild;

    public final boolean enableLog;
    public final int bugReportMarkupLanguage;
    public final int maxCrashReportsSaved;
    public final String versionName;

    public final int versionCode;
    public final String appBuiltType;
    public final String appFlavour;
    public final String scmRevHash;
    public final String scmBranch;
    public final String ciBuildId;
    public final String ciBuildJob;
    public final RecoverBehaviour debugBehaviour;

    public final RecoverBehaviour releaseBehaviour;
    public final CrashDataHandler storage;

    private PlanBConfig(Builder builder) {
        isDebugBuild = builder.isDebugBuild;
        enableLog = builder.enableLog;
        bugReportMarkupLanguage = builder.bugReportMarkupLanguage;
        maxCrashReportsSaved = builder.maxCrashReportsSaved;
        versionName = builder.versionName;
        versionCode = builder.versionCode;
        appBuiltType = builder.appBuiltType;
        appFlavour = builder.appFlavour;
        scmRevHash = builder.scmRevHash;
        scmBranch = builder.scmBranch;
        ciBuildId = builder.ciBuildId;
        ciBuildJob = builder.ciBuildJob;
        debugBehaviour = builder.debugBehaviour;
        releaseBehaviour = builder.releaseBehaviour;
        storage = builder.storage;
    }

    static Builder newBuilder(Context context) {
        return new Builder(context);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanBConfig that = (PlanBConfig) o;

        if (isDebugBuild != that.isDebugBuild) return false;
        if (enableLog != that.enableLog) return false;
        if (bugReportMarkupLanguage != that.bugReportMarkupLanguage) return false;
        if (maxCrashReportsSaved != that.maxCrashReportsSaved) return false;
        if (versionCode != that.versionCode) return false;
        if (versionName != null ? !versionName.equals(that.versionName) : that.versionName != null)
            return false;
        if (appBuiltType != null ? !appBuiltType.equals(that.appBuiltType) : that.appBuiltType != null)
            return false;
        if (appFlavour != null ? !appFlavour.equals(that.appFlavour) : that.appFlavour != null)
            return false;
        if (scmRevHash != null ? !scmRevHash.equals(that.scmRevHash) : that.scmRevHash != null)
            return false;
        if (scmBranch != null ? !scmBranch.equals(that.scmBranch) : that.scmBranch != null)
            return false;
        if (ciBuildId != null ? !ciBuildId.equals(that.ciBuildId) : that.ciBuildId != null)
            return false;
        if (ciBuildJob != null ? !ciBuildJob.equals(that.ciBuildJob) : that.ciBuildJob != null)
            return false;
        if (debugBehaviour != null ? !debugBehaviour.equals(that.debugBehaviour) : that.debugBehaviour != null)
            return false;
        if (releaseBehaviour != null ? !releaseBehaviour.equals(that.releaseBehaviour) : that.releaseBehaviour != null)
            return false;
        return storage != null ? storage.equals(that.storage) : that.storage == null;

    }

    @Override
    public int hashCode() {
        int result = (isDebugBuild ? 1 : 0);
        result = 31 * result + (enableLog ? 1 : 0);
        result = 31 * result + bugReportMarkupLanguage;
        result = 31 * result + maxCrashReportsSaved;
        result = 31 * result + (versionName != null ? versionName.hashCode() : 0);
        result = 31 * result + versionCode;
        result = 31 * result + (appBuiltType != null ? appBuiltType.hashCode() : 0);
        result = 31 * result + (appFlavour != null ? appFlavour.hashCode() : 0);
        result = 31 * result + (scmRevHash != null ? scmRevHash.hashCode() : 0);
        result = 31 * result + (scmBranch != null ? scmBranch.hashCode() : 0);
        result = 31 * result + (ciBuildId != null ? ciBuildId.hashCode() : 0);
        result = 31 * result + (ciBuildJob != null ? ciBuildJob.hashCode() : 0);
        result = 31 * result + (debugBehaviour != null ? debugBehaviour.hashCode() : 0);
        result = 31 * result + (releaseBehaviour != null ? releaseBehaviour.hashCode() : 0);
        result = 31 * result + (storage != null ? storage.hashCode() : 0);
        return result;
    }

    public static final class Builder {
        private boolean isDebugBuild;
        private boolean enableLog;
        private int bugReportMarkupLanguage;
        private int maxCrashReportsSaved;
        private String versionName;
        private int versionCode;
        private String appBuiltType;
        private String appFlavour;
        private String scmRevHash;
        private String scmBranch;
        private String ciBuildId;
        private String ciBuildJob;
        private RecoverBehaviour debugBehaviour;
        private RecoverBehaviour releaseBehaviour;
        private CrashDataHandler storage;

        private Builder(Context context) {
            this.storage = new SharedPrefStorage(context);
            this.isDebugBuild = this.enableLog = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
            this.debugBehaviour = new DefaultBehavior();
            this.releaseBehaviour = new DefaultBehavior();
            this.bugReportMarkupLanguage = ML_MARKDOWN;
            try {
                this.versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                this.versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (Exception e) {
            }
        }

        public Builder isDebugBuild(boolean isDebugBuild) {
            this.isDebugBuild = isDebugBuild;
            return this;
        }

        public Builder enableLog(boolean enableLog) {
            this.enableLog = enableLog;
            return this;
        }

        public Builder bugReportMarkupLanguage(@MarkupLanguage int bugReportMarkupLanguage) {
            this.bugReportMarkupLanguage = bugReportMarkupLanguage;
            return this;
        }

        public Builder maxCrashReportsSaved(int maxCrashReportsSaved) {
            this.maxCrashReportsSaved = maxCrashReportsSaved;
            return this;
        }

        public Builder version(String versionName, int versionCode) {
            this.versionName = versionName;
            this.versionCode = versionCode;
            return this;
        }

        public Builder applicationVariant(String appBuiltType, String appFlavour) {
            this.appBuiltType = appBuiltType;
            this.appFlavour = appFlavour;
            return this;
        }

        public Builder scm(String scmRevHash, @Nullable String scmBranch) {
            this.scmRevHash = scmRevHash;
            this.scmBranch = scmBranch;
            return this;
        }

        public Builder ci(String ciBuildId, @Nullable String ciBuildJob) {
            this.ciBuildId = ciBuildId;
            this.ciBuildJob = ciBuildJob;
            return this;
        }

        public Builder debugBehaviour(RecoverBehaviour debugBehaviour) {
            this.debugBehaviour = debugBehaviour;
            return this;
        }

        public Builder releaseBehaviour(RecoverBehaviour releaseBehaviour) {
            this.releaseBehaviour = releaseBehaviour;
            return this;
        }

        public Builder storage(CrashDataHandler storage) {
            this.storage = storage;
            return this;
        }

        public PlanBConfig build() {
            return new PlanBConfig(this);
        }
    }
}
