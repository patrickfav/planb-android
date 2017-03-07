package at.favre.lib.planb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.favre.lib.planb.data.CrashData;
import at.favre.lib.planb.data.InMemoryCrashDataHandler;
import at.favre.lib.planb.interfaces.CrashDataHandler;
import at.favre.lib.planb.recover.BaseCrashBehaviour;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PlanBUncaughtExceptionHandlerTest {
    @Before
    public void setup() {
        PlanB.destroy();
    }

    @Test
    public void testSimpleCrashHandling() {
        CrashDataHandler crashDataHandler = new InMemoryCrashDataHandler();
        MockCrashAction crashAction = new MockCrashAction();
        final boolean[] wasBehaviourCalled = {false};

        PlanB.get().init(true, PlanB.newConfig(InstrumentationRegistry.getTargetContext()).crashDataHandler(crashDataHandler).build());

        PlanBUncaughtExceptionHandler handler = new PlanBUncaughtExceptionHandler(
                InstrumentationRegistry.getTargetContext(),
                PlanB.get().getConfig(),
                new BaseCrashBehaviour(false, false, true, crashAction, crashAction) {
                    @Override
                    public void handleCrash(@NonNull Context context, @NonNull Thread thread, @NonNull Throwable throwable, @NonNull CrashData crashData, @NonNull PlanBConfig config) {
                        wasBehaviourCalled[0] = true;
                    }
                });

        handler.uncaughtException(Thread.currentThread(), new IllegalStateException());

        assertEquals(1, crashDataHandler.size());
        assertEquals(1, crashDataHandler.getAll().size());
        assertTrue(crashDataHandler.hasUnhandledCrash());
        assertTrue(wasBehaviourCalled[0]);
        assertNotNull(crashDataHandler.getLatest());
        assertEquals(2, crashAction.getCallCounter());
    }


}
