package at.favre.lib.planb.recover;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import at.favre.lib.planb.interfaces.CrashRecoverBehaviour;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CrashRecoverBehaviorTest {

    @Test
    public void testDefaultBehaviour() {
        CrashRecoverBehaviour behaviour = new DefaultBehavior();
        assertNotNull(behaviour.getPostCrashAction());
        assertNotNull(behaviour.getPreCrashAction());
        assertTrue(behaviour.callDefaultExceptionHandler());
        assertFalse(behaviour.killProcess());
        assertTrue(behaviour.persistCrashData());
    }

    @Test
    public void testSuppressBehaviour() {
        CrashRecoverBehaviour behaviour = new SuppressCrashBehaviour();
        assertNotNull(behaviour.getPostCrashAction());
        assertNotNull(behaviour.getPreCrashAction());
        assertFalse(behaviour.callDefaultExceptionHandler());
        assertTrue(behaviour.killProcess());
        assertTrue(behaviour.persistCrashData());
    }

    @Test
    public void testStartActivityBehaviour() {
        CrashRecoverBehaviour behaviour = new StartActivityBehaviour(null);
        assertNotNull(behaviour.getPostCrashAction());
        assertNotNull(behaviour.getPreCrashAction());
        assertFalse(behaviour.callDefaultExceptionHandler());
        assertTrue(behaviour.killProcess());
        assertTrue(behaviour.persistCrashData());
    }
}
