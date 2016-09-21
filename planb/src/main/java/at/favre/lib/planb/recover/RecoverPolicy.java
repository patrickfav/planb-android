package at.favre.lib.planb.recover;

public class RecoverPolicy {
    private final BuildTypeMatcher matcher;
    private final RecoverBehaviour behaviour;

    public RecoverPolicy(BuildTypeMatcher matcher, RecoverBehaviour behaviour) {
        this.matcher = matcher;
        this.behaviour = behaviour;
    }

    public BuildTypeMatcher getMatcher() {
        return matcher;
    }

    public RecoverBehaviour getBehaviour() {
        return behaviour;
    }
}
