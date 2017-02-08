package at.favre.lib.planb.parser;

/**
 * Created by PatrickF on 08.02.2017.
 */

public class TextileRenderer implements MarkupRenderer {
    @Override
    public String renderHeader() {
        return "";
    }

    @Override
    public String render(String content, @GenericMLParser.MarkupToken int tokenType) {

        switch (tokenType) {
            case GenericMLParser.TOKEN_HEADER1:
                return "h1. " + content + "\n";
            case GenericMLParser.TOKEN_HEADER2:
                return "h2. " + content + "\n";
            case GenericMLParser.TOKEN_HEADER3:
                return "h3. " + content + "\n";
            case GenericMLParser.TOKEN_STRONG:
                return "*" + content + "*";
            case GenericMLParser.TOKEN_ITALIC:
                return "_" + content + "_";
            case GenericMLParser.TOKEN_CODE:
                return "@" + content + "@";
            case GenericMLParser.TOKEN_CODEBLOCK:
                return "<pre> " + content + "</pre>";
            case GenericMLParser.TOKEN_QUOTE:
                return "bq. " + content;
        }
        return "unknown command (" + content + ")";
    }

    @Override
    public String renderFooter() {
        return "";
    }

    @Override
    @MarkupLanguage
    public int getType() {
        return ML_TEXTILE;
    }
}
