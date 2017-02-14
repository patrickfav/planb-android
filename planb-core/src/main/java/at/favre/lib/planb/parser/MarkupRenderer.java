package at.favre.lib.planb.parser;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Interface for implementing markup render with the generic markup language
 * provided in {@link GenericMLParser}
 */
public interface MarkupRenderer {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ML_MARKDOWN, ML_TEXTILE, ML_ASCIIDOC, ML_HTML})
    @interface MarkupLanguage {
    }

    int ML_MARKDOWN = 0;
    int ML_TEXTILE = 1;
    int ML_ASCIIDOC = 2;
    int ML_HTML = 3;

    /**
     * Add a document header if necessary
     *
     * @return a header or empty string
     */
    @NonNull
    String renderHeader();

    /**
     * Renders the equivalent of given token in the specific language
     *
     * @param content   as string
     * @param tokenType as defined in {@link GenericMLParser.MarkupToken}
     * @return the rendered string in the implemented markup language
     */
    String render(String content, @GenericMLParser.MarkupToken int tokenType);

    /**
     * Add a document footer if necessary
     *
     * @return a footer or empty string
     */
    @NonNull
    String renderFooter();

    /**
     * The type as defined in {@link MarkupLanguage}
     * @return type as int
     */
    @MarkupLanguage
    int getType();

    class Util {
        public static MarkupRenderer getById(@MarkupLanguage int languageId) {
            switch (languageId) {
                case ML_MARKDOWN:
                    return new MarkdownRenderer();
                case ML_TEXTILE:
                    return new TextileRenderer();
                case ML_ASCIIDOC:
                    return new AsciiDocRenderer();
                case ML_HTML:
                    return new HTML5Renderer();
                default:
                    throw new IllegalArgumentException("unknown markup language " + languageId);
            }
        }
    }
}

