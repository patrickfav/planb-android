package at.favre.lib.planb;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.favre.lib.planb.data.InMemoryCrashDataHandler;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class PlanBTest {

    @Before
    public void setup() {
        PlanB.destroy();
    }

    @Test
    public void testCreation() {
        assertNotNull(PlanB.get());
        assertNotNull(PlanB.newConfig(InstrumentationRegistry.getTargetContext()));
    }

    @Test
    public void testInit() {
        PlanB planB = PlanB.get();
        planB.init(InstrumentationRegistry.getTargetContext());
        planB.enableCrashHandler(InstrumentationRegistry.getTargetContext(), PlanB.behaviourFactory().createSuppressCrashBehaviour());
        assertNotNull(planB.getCrashDataHandler());
        planB.disableCrashHandler();
    }

    @Test
    public void testInit2() {
        PlanB planB = PlanB.get();
        planB.init(true, PlanB.newConfig(InstrumentationRegistry.getTargetContext()).debugCrashDataHandler(PlanB.crashDataHandlerFactory().createInMemoryHandler()).build());
        planB.enableCrashHandler(InstrumentationRegistry.getTargetContext(), PlanB.behaviourFactory().createSuppressCrashBehaviour(), PlanB.behaviourFactory().createSuppressCrashBehaviour());
        assertTrue(planB.isDebugBuild());
        assertTrue(planB.getCrashDataHandler() instanceof InMemoryCrashDataHandler);
    }

    @Test
    public void testEnableMustInitFirst() {
        PlanB planB = PlanB.get();
        try {
            planB.enableCrashHandler(InstrumentationRegistry.getTargetContext(), PlanB.behaviourFactory().createSuppressCrashBehaviour());
            fail();
        } catch (Exception e) {

        }
    }
}
