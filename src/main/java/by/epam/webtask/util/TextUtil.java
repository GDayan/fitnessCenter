package by.epam.webtask.util;
import org.apache.commons.text.StringEscapeUtils;

public final class TextUtil {
    private TextUtil() {}

    public static String escapeText(String text) {
        return StringEscapeUtils.escapeHtml4(text);
    }
}
