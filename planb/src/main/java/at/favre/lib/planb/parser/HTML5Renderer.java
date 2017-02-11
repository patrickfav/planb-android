package at.favre.lib.planb.parser;

import android.support.annotation.NonNull;

/**
 * A renderer for basic HTML5
 */
public class HTML5Renderer implements MarkupRenderer {
    @NonNull
    @Override
    public String renderHeader() {
        return "<!doctype html><html><head></head><body>\n";
    }

    @Override
    public String render(String content, @GenericMLParser.MarkupToken int tokenType) {

        switch (tokenType) {
            case GenericMLParser.TOKEN_HEADER1:
                return "<h1>" + content + "<h1>\n";
            case GenericMLParser.TOKEN_HEADER2:
                return "<h2>" + content + "<h2>\n";
            case GenericMLParser.TOKEN_HEADER3:
                return "<h3>" + content + "<h3>\n";
            case GenericMLParser.TOKEN_STRONG:
                return "<strong>" + content + "</strong>";
            case GenericMLParser.TOKEN_ITALIC:
                return "<em>" + content + "</em>";
            case GenericMLParser.TOKEN_CODE:
                return "<code>" + content + "<code>";
            case GenericMLParser.TOKEN_CODEBLOCK:
                return "<pre>" + content + "</pre>\n";
            case GenericMLParser.TOKEN_QUOTE:
                return "<blockquote>" + content + "</blockquote>\n";
        }

        return "unknown command (" + content + ")";
    }

    @NonNull
    @Override
    public String renderFooter() {
        return "\n</body></html>";
    }

    @Override
    @MarkupLanguage
    public int getType() {
        return ML_HTML;
    }
}
