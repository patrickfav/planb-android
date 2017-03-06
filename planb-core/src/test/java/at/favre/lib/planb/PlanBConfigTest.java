package at.favre.lib.planb;


import org.junit.Test;

import at.favre.lib.planb.data.InMemoryCrashDataHandler;
import at.favre.lib.planb.interfaces.CrashDataHandler;
import at.favre.lib.planb.parser.MarkupRenderer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;

public class PlanBConfigTest {

    @Test
    public void testConfigBuilder() {
        CrashDataHandler handler = new InMemoryCrashDataHandler();
        PlanBConfig config = PlanBConfig.newBuilder()
                .applicationVariant("buildType", "flavor")
                .bugReportMarkupLanguage(MarkupRenderer.ML_ASCIIDOC)
                .ci("cibuildid", "cijob")
                .enableLog(true)
                .scm("scmRevHash", "scmBranch")
                .version("versionName", 1)
                .debugCrashDataHandler(handler)
                .releaseCrashDataHandler(null)
                .build();

        assertEquals("flavor", config.appFlavour);
        assertEquals("buildType", config.appBuiltType);
        assertEquals("cibuildid", config.ciBuildId);
        assertEquals("cijob", config.ciBuildJob);
        assertEquals("scmBranch", config.scmBranch);
        assertEquals("scmRevHash", config.scmRevHash);
        assertEquals("versionName", config.versionName);
        assertEquals(1, config.versionCode);
        assertEquals(true, config.enableLog);
        assertEquals(MarkupRenderer.ML_ASCIIDOC, config.bugReportMarkupLanguage);
        assertSame(handler, config.debugCrashDataHandler);
        assertNull(config.releaseCrashDataHandler);
    }

    @Test
    public void testEqualsAndHash() {
        CrashDataHandler handler = new InMemoryCrashDataHandler();
        PlanBConfig config1 = PlanBConfig.newBuilder()
                .applicationVariant("buildType", "flavor")
                .bugReportMarkupLanguage(MarkupRenderer.ML_TEXTILE)
                .ci(null, "cijob")
                .enableLog(true)
                .scm("2adasd", null)
                .version("versionName", 5656)
                .debugCrashDataHandler(handler)
                .releaseCrashDataHandler(handler)
                .build();

        PlanBConfig config2 = PlanBConfig.newBuilder()
                .applicationVariant("buildType", "flavor")
                .bugReportMarkupLanguage(MarkupRenderer.ML_TEXTILE)
                .ci(null, "cijob")
                .enableLog(true)
                .scm("2adasd", null)
                .version("versionName", 5656)
                .crashDataHandler(handler)
                .build();

        assertEquals(config1, config2);
        assertEquals(config1.hashCode(), config2.hashCode());
    }
}
