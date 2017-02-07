package at.favre.lib.planb.parser;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class GenericMLParser {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TOKEN_HEADER1, TOKEN_HEADER2, TOKEN_HEADER3, TOKEN_STRONG, TOKEN_CODEBLOCK})
    public @interface MarkupToken {
    }

    public static final int TOKEN_HEADER1 = 0;
    public static final int TOKEN_HEADER2 = 1;
    public static final int TOKEN_HEADER3 = 2;
    public static final int TOKEN_STRONG = 3;
    public static final int TOKEN_CODEBLOCK = 4;

    public final static Map<Integer, String> genericTokens = new HashMap<>();

    static {
        genericTokens.put(TOKEN_HEADER1, "__header1");
        genericTokens.put(TOKEN_HEADER2, "__header2");
        genericTokens.put(TOKEN_HEADER3, "__header3");
        genericTokens.put(TOKEN_STRONG, "__strong");
        genericTokens.put(TOKEN_CODEBLOCK, "__codeBlock");
    }

    private String fillPlaceholder(String text, Map<String, String> values) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            text = text.replaceAll("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return text;
    }

    private String render(String text, MarkupRenderer renderer) {
        for (Map.Entry<Integer, String> token : genericTokens.entrySet()) {
            Pattern.compile(token.getValue() + "\\((.*?)\\)").matcher(text);
        }

        return null;
    }

}
