package at.favre.lib.planb;

import android.content.Context;
import android.support.annotation.Nullable;

import at.favre.lib.planb.parser.MarkupRenderer;
import at.favre.lib.planb.recover.CrashRecoverBehaviour;
import at.favre.lib.planb.recover.DefaultBehavior;

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
    public final CrashRecoverBehaviour debugBehaviour;

    public final CrashRecoverBehaviour releaseBehaviour;

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
        debugBehaviour = builder.debugBehaviour;
        releaseBehaviour = builder.releaseBehaviour;
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
        if (ciBuildJob != null ? !ciBuildJob.equals(that.ciBuildJob) : that.ciBuildJob != null)
            return false;
        if (debugBehaviour != null ? !debugBehaviour.equals(that.debugBehaviour) : that.debugBehaviour != null)
            return false;
        return releaseBehaviour != null ? !releaseBehaviour.equals(that.releaseBehaviour) : that.releaseBehaviour != null;
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
        result = 31 * result + (debugBehaviour != null ? debugBehaviour.hashCode() : 0);
        result = 31 * result + (releaseBehaviour != null ? releaseBehaviour.hashCode() : 0);
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
        private CrashRecoverBehaviour debugBehaviour;
        private CrashRecoverBehaviour releaseBehaviour;

        private Builder(Context context) {
            this.debugBehaviour = new DefaultBehavior();
            this.releaseBehaviour = new DefaultBehavior();
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

        public Builder debugBehaviour(CrashRecoverBehaviour debugBehaviour) {
            this.debugBehaviour = debugBehaviour;
            return this;
        }

        public Builder releaseBehaviour(CrashRecoverBehaviour releaseBehaviour) {
            this.releaseBehaviour = releaseBehaviour;
            return this;
        }

        public Builder behaviour(CrashRecoverBehaviour behaviour) {
            this.debugBehaviour = behaviour;
            this.releaseBehaviour = behaviour;
            return this;
        }

        public PlanBConfig build() {
            return new PlanBConfig(this);
        }
    }
}
