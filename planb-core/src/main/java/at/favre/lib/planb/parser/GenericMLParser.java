package at.favre.lib.planb.parser;


import android.support.annotation.IntDef;
import android.support.annotation.VisibleForTesting;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Parses the generic markup language that is used as intermediate for other
 * markup languages.
 */
public class GenericMLParser {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TOKEN_HEADER1, TOKEN_HEADER2, TOKEN_HEADER3, TOKEN_STRONG, TOKEN_ITALIC, TOKEN_CODE, TOKEN_CODEBLOCK, TOKEN_QUOTE})
    public @interface MarkupToken {
    }

    public static final int TOKEN_HEADER1 = 0;
    public static final int TOKEN_HEADER2 = 1;
    public static final int TOKEN_HEADER3 = 2;
    public static final int TOKEN_STRONG = 3;
    public static final int TOKEN_ITALIC = 4;
    public static final int TOKEN_CODE = 5;
    public static final int TOKEN_CODEBLOCK = 6;
    public static final int TOKEN_QUOTE = 7;

    public final static Map<String, Integer> genericTokens = new HashMap<>();

    static {
        genericTokens.put("header1", TOKEN_HEADER1);
        genericTokens.put("header2", TOKEN_HEADER2);
        genericTokens.put("header3", TOKEN_HEADER3);
        genericTokens.put("strong", TOKEN_STRONG);
        genericTokens.put("italic", TOKEN_ITALIC);
        genericTokens.put("code", TOKEN_CODE);
        genericTokens.put("codeBlock", TOKEN_CODEBLOCK);
        genericTokens.put("quote", TOKEN_QUOTE);
    }

    public String render(String template, MarkupRenderer renderer, Map<String, String> placeHolderValues) {
        StringBuilder out = new StringBuilder();
        out.append(renderer.renderHeader());
        out.append(fillPlaceholder(renderTemplate(template, renderer), placeHolderValues));
        out.append(renderer.renderFooter());
        return out.toString();
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    String fillPlaceholder(String text, Map<String, String> values) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            System.out.println(entry.getKey() + " replace with " + entry.getValue());
            text = text.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return text;
    }

    public String renderTemplate(String template, MarkupRenderer renderer) {
        StringBuilder out = new StringBuilder();
        char[] chars = template.toCharArray();

        boolean commandMode = false;
        boolean contentMode = false;
        StringBuilder command = new StringBuilder();
        StringBuilder content = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            if (contentMode) {
                if (chars[i] == ')') {
                    //noinspection WrongConstant
                    out.append(renderer.render(content.toString(), genericTokens.get(command.toString())));

                    command.setLength(0);
                    content.setLength(0);
                    commandMode = false;
                    contentMode = false;
                } else {
                    content.append(chars[i]);
                }
                continue;
            }


            if (commandMode) {
                if (chars[i] == '(') {
                    contentMode = true;
                } else {
                    command.append(chars[i]);
                }
                continue;
            }

            if (chars[i] == '_' && i + 1 < chars.length && chars[i + 1] == '_') {
                commandMode = true;
                i += 1;
                continue;
            }

            out.append(chars[i]);
        }

        return out.toString();
    }
}
