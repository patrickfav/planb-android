package at.favre.lib.planb.parser;

import org.junit.Test;

public class GenericMLParserTest {
    @Test
    public void addition_isCorrect() throws Exception {
        GenericMLParser parser = new GenericMLParser();

        String rendered = parser.renderTemplate("__strong(this is sooo string)\nhallo nothing\n__code(AClass)", new MarkdownRenderer());
        System.out.println(rendered);
    }

    @Test
    public void testAll() throws Exception {
        GenericMLParser parser = new GenericMLParser();

        MarkupRenderer[] rendererList = new MarkupRenderer[]{new MarkdownRenderer(), new AsciiDocRenderer(), new TextileRenderer(), new HTML5Renderer()};
        for (MarkupRenderer markupRenderer : rendererList) {
            System.out.println(markupRenderer.getClass().getName());
            String rendered = parser.renderTemplate("__header1(Bugreport)\n" +
                    "\n" +
                    "__header2(Testobject)\n" +
                    "\n" +
                    "Timestamp: {{timestamp}}\n" +
                    "\n" +
                    "__header3(App Version)\n" +
                    "\n" +
                    "Version: {{version}}\n" +
                    "\n" +
                    "SCM: {{scm}}\n" +
                    "\n" +
                    "CI: {{ci}}\n" +
                    "\n" +
                    "__header3(Device)\n" +
                    "\n" +
                    "Model: {{device}}\n" +
                    "\n" +
                    "Android: {{android_version}}\n" +
                    "\n" +
                    "Serial: {{device_serial}}\n" +
                    "\n" +
                    "__header2(Description)\n" +
                    "\n" +
                    "__strong(Preconditions:)\n" +
                    "\n" +
                    "__strong(Observed Behaviour:)\n" +
                    "\n" +
                    "__strong(Expected:)\n" +
                    "\n" +
                    "__header2(Stacktrace)\n" +
                    "\n" +
                    "__strong({{exception}})\n" +
                    "\n" +
                    "__codeBlock({{stacktrace}})", markupRenderer);
            System.out.println(rendered);
        }
    }
}
