package at.favre.lib.planb.util;

import org.junit.Test;

import at.favre.lib.planb.exceptions.MockRuntimeException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class CrashUtilTest {

    @Test(expected = MockRuntimeException.class)
    public void testMockCrash() {
        CrashUtil.crash();
    }

    @Test
    public void testPrintStack() {
        String stack = CrashUtil.printStacktrace(new IllegalStateException());

        assertTrue(stack.contains("IllegalStateException"));
        assertNotNull(stack);
    }
}
