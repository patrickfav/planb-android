package at.favre.lib.planb.parser;

public interface MarkupRenderer {

    String renderHeader();

    String render(String content, @GenericMLParser.MarkupToken int tokenType);

    String renderFooter();
}

