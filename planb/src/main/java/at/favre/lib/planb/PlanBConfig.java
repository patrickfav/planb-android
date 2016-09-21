package at.favre.lib.planb;

import java.util.ArrayList;
import java.util.List;

import at.favre.lib.planb.recover.BuildTypeMatcher;
import at.favre.lib.planb.recover.DefaultBehavior;
import at.favre.lib.planb.recover.RecoverPolicy;
import at.favre.lib.planb.recover.RestartActivityBehaviour;

public class PlanBConfig {
    private List<RecoverPolicy> policyList = new ArrayList<>();

    public PlanBConfig addDefault() {
        policyList.add(new RecoverPolicy(new BuildTypeMatcher.Debug(), new DefaultBehavior()));
        policyList.add(new RecoverPolicy(new BuildTypeMatcher.NonDebug(), new RestartActivityBehaviour()));
        return this;
    }

}
