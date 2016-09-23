package at.favre.lib.planb;

import android.content.Context;

import at.favre.lib.planb.data.PersistenceStorage;
import at.favre.lib.planb.data.SharedPrefStorage;
import at.favre.lib.planb.recover.DefaultBehavior;
import at.favre.lib.planb.recover.RecoverBehaviour;
import at.favre.lib.planb.recover.RestartActivityBehaviour;

public class PlanBConfig {
    public final boolean debug;
    public final boolean enableLog;
    public final RecoverBehaviour debugBehaviour;
    public final RecoverBehaviour releaseBehaviour;
    public final PersistenceStorage storage;

    private PlanBConfig(boolean debug, boolean enableLog, RecoverBehaviour debugBehaviour, RecoverBehaviour releaseBehaviour, PersistenceStorage storage) {
        this.debug = debug;
        this.enableLog = enableLog;
        this.debugBehaviour = debugBehaviour;
        this.releaseBehaviour = releaseBehaviour;
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanBConfig that = (PlanBConfig) o;

        if (debug != that.debug) return false;
        if (enableLog != that.enableLog) return false;
        if (debugBehaviour != null ? !debugBehaviour.equals(that.debugBehaviour) : that.debugBehaviour != null)
            return false;
        if (releaseBehaviour != null ? !releaseBehaviour.equals(that.releaseBehaviour) : that.releaseBehaviour != null)
            return false;
        return storage != null ? storage.equals(that.storage) : that.storage == null;

    }

    @Override
    public int hashCode() {
        int result = (debug ? 1 : 0);
        result = 31 * result + (enableLog ? 1 : 0);
        result = 31 * result + (debugBehaviour != null ? debugBehaviour.hashCode() : 0);
        result = 31 * result + (releaseBehaviour != null ? releaseBehaviour.hashCode() : 0);
        result = 31 * result + (storage != null ? storage.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private boolean debug = true;
        private boolean enableLog = debug;
        private RecoverBehaviour debugBehaviour = new DefaultBehavior();
        private RecoverBehaviour releaseBehaviour = new RestartActivityBehaviour();
        private PersistenceStorage storage;

        Builder(Context context) {
            this.storage = new SharedPrefStorage(context);
        }

        public Builder debugMode(boolean enabled) {
            this.debug = enabled;
            return this;
        }

        public Builder enableLog(boolean enabled) {
            this.enableLog = enabled;
            return this;
        }

        public Builder debugCrashBehaviour(RecoverBehaviour behaviour) {
            this.debugBehaviour = behaviour;
            return this;
        }

        public Builder releaseCrashBehaviour(RecoverBehaviour behaviour) {
            this.releaseBehaviour = behaviour;
            return this;
        }

        public Builder storageEngine(PersistenceStorage crashStorage) {
            this.storage = crashStorage;
            return this;
        }

        public PlanBConfig build() {
            return new PlanBConfig(debug, enableLog, debugBehaviour, releaseBehaviour, storage);
        }
    }

}
