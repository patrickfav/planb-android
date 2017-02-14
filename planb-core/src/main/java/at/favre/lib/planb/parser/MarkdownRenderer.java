package at.favre.lib.planb.parser;

import android.support.annotation.NonNull;

/**
 * Renderer for https://daringfireball.net/projects/markdown/
 */
public class MarkdownRenderer implements MarkupRenderer {
    @NonNull
    @Override
    public String renderHeader() {
        return "";
    }

    @Override
    public String render(String content, @GenericMLParser.MarkupToken int tokenType) {

        switch (tokenType) {
            case GenericMLParser.TOKEN_HEADER1:
                return "# " + content;
            case GenericMLParser.TOKEN_HEADER2:
                return "## " + content;
            case GenericMLParser.TOKEN_HEADER3:
                return "### " + content;
            case GenericMLParser.TOKEN_STRONG:
                return "**" + content + "**";
            case GenericMLParser.TOKEN_ITALIC:
                return "*" + content + "*";
            case GenericMLParser.TOKEN_CODE:
                return "`" + content + "`";
            case GenericMLParser.TOKEN_CODEBLOCK:
                return "```\n" + content + "\n```";
            case GenericMLParser.TOKEN_QUOTE:
                return "> " + content;
        }

        return "unknown command (" + content + ")";
    }

    @NonNull
    @Override
    public String renderFooter() {
        return "";
    }

    @Override
    @MarkupLanguage
    public int getType() {
        return ML_MARKDOWN;
    }
}
