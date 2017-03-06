package at.favre.lib.planb;


import org.junit.Before;
import org.junit.Test;

import at.favre.lib.planb.interfaces.CrashRecoverBehaviour;
import at.favre.lib.planb.interfaces.RecoverBehaviorFactory;
import at.favre.lib.planb.recover.DefaultBehavior;
import at.favre.lib.planb.recover.StartActivityBehaviour;
import at.favre.lib.planb.recover.SuppressCrashBehaviour;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class PlanBRecoverBehaviorFactoryTest {

    private RecoverBehaviorFactory factory;

    @Before
    public void setup() {
        factory = PlanB.behaviourFactory();
    }

    @Test
    public void testCreateDefaultBehaviors() {
        check(factory.createDefaultHandlerBehaviour(), DefaultBehavior.class);
        check(factory.createDefaultHandlerBehaviour(new CrashRecoverBehaviour.CrashAction.Noop(), new CrashRecoverBehaviour.CrashAction.Noop()), DefaultBehavior.class);
        check(factory.createDefaultHandlerBehaviour(null, null), DefaultBehavior.class);
        check(factory.createDefaultHandlerBehaviour(null, new CrashRecoverBehaviour.CrashAction.Noop()), DefaultBehavior.class);
    }

    @Test
    public void testCreateSuppressBehaviors() {
        check(factory.createSuppressCrashBehaviour(), SuppressCrashBehaviour.class);
        check(factory.createSuppressCrashBehaviour(new CrashRecoverBehaviour.CrashAction.Noop(), new CrashRecoverBehaviour.CrashAction.Noop()), SuppressCrashBehaviour.class);
        check(factory.createSuppressCrashBehaviour(null, null), SuppressCrashBehaviour.class);
        check(factory.createSuppressCrashBehaviour(null, new CrashRecoverBehaviour.CrashAction.Noop()), SuppressCrashBehaviour.class);
    }

    @Test
    public void testCreateStartActivityBehaviors() {
        check(factory.createStartActivityCrashBehaviour(null), StartActivityBehaviour.class);
        check(factory.createStartActivityCrashBehaviour(null, new CrashRecoverBehaviour.CrashAction.Noop(), new CrashRecoverBehaviour.CrashAction.Noop()), StartActivityBehaviour.class);
        check(factory.createStartActivityCrashBehaviour(null, null, null), StartActivityBehaviour.class);
        check(factory.createStartActivityCrashBehaviour(null, null, new CrashRecoverBehaviour.CrashAction.Noop()), StartActivityBehaviour.class);
    }

    @Test
    public void testCreateRestartForegroundActivityBehaviors() {
        check(factory.createRestartForegroundActivityCrashBehaviour(), StartActivityBehaviour.class);
        check(factory.createRestartForegroundActivityCrashBehaviour(new CrashRecoverBehaviour.CrashAction.Noop(), new CrashRecoverBehaviour.CrashAction.Noop()), StartActivityBehaviour.class);
        check(factory.createRestartForegroundActivityCrashBehaviour(null, null), StartActivityBehaviour.class);
        check(factory.createRestartForegroundActivityCrashBehaviour(null, new CrashRecoverBehaviour.CrashAction.Noop()), StartActivityBehaviour.class);
    }

    @Test
    public void testCreateRestartLauncherActivityBehaviors() {
        check(factory.createRestartLauncherActivityCrashBehaviour(), StartActivityBehaviour.class);
    }

    private void check(CrashRecoverBehaviour behaviour, Class<? extends CrashRecoverBehaviour> expectedClass) {
        assertNotNull(behaviour);
        assertNotNull(behaviour.getPostCrashAction());
        assertNotNull(behaviour.getPreCrashAction());
        assertEquals(expectedClass, behaviour.getClass());
        behaviour.callDefaultExceptionHandler();
        behaviour.killProcess();
        behaviour.persistCrashData();
    }
}
