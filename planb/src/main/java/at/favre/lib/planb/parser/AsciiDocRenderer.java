package at.favre.lib.planb.parser;


public class AsciiDocRenderer implements MarkupRenderer {
    @Override
    public String renderHeader() {
        return "";
    }

    @Override
    public String render(String content, @GenericMLParser.MarkupToken int tokenType) {

        switch (tokenType) {
            case GenericMLParser.TOKEN_HEADER1:
                return "= " + content;
            case GenericMLParser.TOKEN_HEADER2:
                return "== " + content;
            case GenericMLParser.TOKEN_HEADER3:
                return "=== " + content;
            case GenericMLParser.TOKEN_STRONG:
                return "*" + content + "*";
            case GenericMLParser.TOKEN_ITALIC:
                return "_" + content + "_";
            case GenericMLParser.TOKEN_CODE:
                return "`" + content + "`";
            case GenericMLParser.TOKEN_CODEBLOCK:
                return "\n----\n" + content + "\n----\n";
            case GenericMLParser.TOKEN_QUOTE:
                return "\n____\n" + content + "\n____\n";
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
        return ML_ASCIIDOC;
    }
}
