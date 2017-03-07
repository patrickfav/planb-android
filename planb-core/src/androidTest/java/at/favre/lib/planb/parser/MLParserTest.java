package at.favre.lib.planb.parser;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import at.favre.lib.planb.MockDataGenerator;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MLParserTest {
    private static final String GENERIC_ML_BUGREPORT = "__header1(Bugreport)\n\n__header2(Testobject)\n\nTimestamp: {{timestamp}}\n\n__header3(App Version)\n\nVersion: {{version}}\n\nSCM: __code({{scm}})\n\nCI: {{ci}}\n\n__header3(Device)\n\nModel: __italic({{device}})\n\nAndroid: {{android_version}}\n\nSerial: __quote({{device_serial}})\n\n__header2(Description)\n\n__strong(Preconditions:)\n\n__strong(Observed Behaviour:)\n\n__strong(Expected:)\n\n__header2(Stacktrace)\n\n__strong({{exception}})\n\n__codeBlock({{stacktrace}})";

    @Test
    public void testMarkDown() throws Exception {
        GenericMLParser parser = new GenericMLParser();

        String rendered = parser.renderTemplate("__strong(this is sooo string)\nhallo nothing\n__code(AClass)", new MarkdownRenderer());
        System.out.println(rendered);
    }

    @Test
    public void testAllParser() throws Exception {
        GenericMLParser parser = new GenericMLParser();

        MarkupRenderer[] rendererList = new MarkupRenderer[]{new MarkdownRenderer(), new AsciiDocRenderer(), new TextileRenderer(), new HTML5Renderer()};
        for (MarkupRenderer markupRenderer : rendererList) {
            System.out.println(markupRenderer.getClass().getName());
            String rendered = parser.renderTemplate(GENERIC_ML_BUGREPORT, markupRenderer);
            System.out.println(rendered);
            assertNotNull(rendered);
            assertTrue(rendered.length() > 20);
        }
    }

    @Test
    public void testPlaceholderHandler() {
        BugReportPlaceholderHandler handler = new BugReportPlaceholderHandler(MockDataGenerator.createCrashData(new IllegalStateException()));
        assertNotNull(handler.getPlaceHolderMap());
        assertTrue(handler.getPlaceHolderMap().size() > 3);
    }

    @Test
    public void testGenericMLRenderer() {
        Map<String, String> placeHolderMap = new BugReportPlaceholderHandler(MockDataGenerator.createCrashData(new IllegalStateException())).getPlaceHolderMap();
        GenericMLParser parser = new GenericMLParser();
        MarkupRenderer[] rendererList = new MarkupRenderer[]{MarkupRenderer.Util.getById(MarkupRenderer.ML_ASCIIDOC), MarkupRenderer.Util.getById(MarkupRenderer.ML_HTML), MarkupRenderer.Util.getById(MarkupRenderer.ML_MARKDOWN), MarkupRenderer.Util.getById(MarkupRenderer.ML_TEXTILE)};

        for (MarkupRenderer markupRenderer : rendererList) {
            String rendered = parser.render(GENERIC_ML_BUGREPORT, markupRenderer, placeHolderMap);
            assertNotNull(rendered);
            assertTrue(rendered.length() > 20);
            assertTrue(rendered.contains(IllegalStateException.class.getName()));
        }
    }
}
