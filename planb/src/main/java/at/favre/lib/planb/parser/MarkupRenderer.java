package at.favre.lib.planb.parser;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface MarkupRenderer {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ML_MARKDOWN, ML_TEXTILE, ML_ASCIIDOC, ML_HTML})
    @interface MarkupLanguage {
    }

    int ML_MARKDOWN = 0;
    int ML_TEXTILE = 1;
    int ML_ASCIIDOC = 2;
    int ML_HTML = 3;

    String renderHeader();

    String render(String content, @GenericMLParser.MarkupToken int tokenType);

    String renderFooter();

    @MarkupLanguage
    int getType();
}

