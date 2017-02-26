package at.favre.lib.planb;

import android.content.Context;
import android.support.annotation.Nullable;

import at.favre.lib.planb.data.CrashDataHandler;
import at.favre.lib.planb.data.SharedPrefCrashDataHandler;
import at.favre.lib.planb.parser.MarkupRenderer;

public class PlanBConfig {
    public final boolean enableLog;
    public final int bugReportMarkupLanguage;
    public final String versionName;

    public final int versionCode;
    public final String appBuiltType;
    public final String appFlavour;
    public final String scmRevHash;
    public final String scmBranch;
    public final String ciBuildId;
    public final String ciBuildJob;

    public final CrashDataHandler debugCrashDataHandler;
    public final CrashDataHandler releaseCrashDataHandler;

    private PlanBConfig(Builder builder) {
        enableLog = builder.enableLog;
        bugReportMarkupLanguage = builder.bugReportMarkupLanguage;
        versionName = builder.versionName;
        versionCode = builder.versionCode;
        appBuiltType = builder.appBuiltType;
        appFlavour = builder.appFlavour;
        scmRevHash = builder.scmRevHash;
        scmBranch = builder.scmBranch;
        ciBuildId = builder.ciBuildId;
        ciBuildJob = builder.ciBuildJob;
        debugCrashDataHandler = builder.debugCrashDataHandler;
        releaseCrashDataHandler = builder.releaseCrashDataHandler;
    }

    static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanBConfig that = (PlanBConfig) o;

        if (enableLog != that.enableLog) return false;
        if (bugReportMarkupLanguage != that.bugReportMarkupLanguage) return false;
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
        return ciBuildJob != null ? ciBuildJob.equals(that.ciBuildJob) : that.ciBuildJob == null;
    }

    @Override
    public int hashCode() {
        int result = (enableLog ? 1 : 0);
        result = 31 * result + bugReportMarkupLanguage;
        result = 31 * result + (versionName != null ? versionName.hashCode() : 0);
        result = 31 * result + versionCode;
        result = 31 * result + (appBuiltType != null ? appBuiltType.hashCode() : 0);
        result = 31 * result + (appFlavour != null ? appFlavour.hashCode() : 0);
        result = 31 * result + (scmRevHash != null ? scmRevHash.hashCode() : 0);
        result = 31 * result + (scmBranch != null ? scmBranch.hashCode() : 0);
        result = 31 * result + (ciBuildId != null ? ciBuildId.hashCode() : 0);
        result = 31 * result + (ciBuildJob != null ? ciBuildJob.hashCode() : 0);
        return result;
    }

    public static final class Builder {
        private boolean enableLog;
        private int bugReportMarkupLanguage;
        private String versionName;
        private int versionCode;
        private String appBuiltType;
        private String appFlavour;
        private String scmRevHash;
        private String scmBranch;
        private String ciBuildId;
        private String ciBuildJob;
        private CrashDataHandler debugCrashDataHandler;
        private CrashDataHandler releaseCrashDataHandler;

        private Builder(Context context) {
            this.debugCrashDataHandler = releaseCrashDataHandler = new SharedPrefCrashDataHandler(context);
            this.bugReportMarkupLanguage = MarkupRenderer.ML_MARKDOWN;
            try {
                this.versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                this.versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (Exception e) {
            }
        }

        public Builder enableLog(boolean enableLog) {
            this.enableLog = enableLog;
            return this;
        }

        public Builder bugReportMarkupLanguage(@MarkupRenderer.MarkupLanguage int bugReportMarkupLanguage) {
            this.bugReportMarkupLanguage = bugReportMarkupLanguage;
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

        public Builder crashDataHandler(CrashDataHandler crashDataHandler) {
            this.debugCrashDataHandler = crashDataHandler;
            this.releaseCrashDataHandler = crashDataHandler;
            return this;
        }

        public Builder debugCrashDataHandler(CrashDataHandler crashDataHandler) {
            this.debugCrashDataHandler = crashDataHandler;
            return this;
        }

        public Builder releaseCrashDataHandler(CrashDataHandler crashDataHandler) {
            this.releaseCrashDataHandler = crashDataHandler;
            return this;
        }

        public PlanBConfig build() {
            return new PlanBConfig(this);
        }
    }
}
