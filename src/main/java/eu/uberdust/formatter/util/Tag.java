package eu.uberdust.formatter.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 3/13/12
 * Time: 4:17 PM
 */
public class Tag {
    private final static String TAG_START = "<";
    private final static String TAG_START_CLOSE = "</";
    private final static String TAG_END = ">";

    final String tagName;
    private final Map<String, String> parameters;
    final StringBuilder contents;

    public Tag(final String tagName) {
        this.tagName = tagName;
        parameters = new HashMap<String, String>();
        contents = new StringBuilder();
    }

    public Tag(final String tagName,final String cont) {
        this.tagName = tagName;
        parameters = new HashMap<String, String>();
        contents = new StringBuilder();
        contents.append(cont);
    }

    public void addParameter(final String key, final String value) {
        parameters.put(key, value);
    }

    public void add(final String content) {
        contents.append(content);
    }


    @Override
    public String toString() {
        final StringBuilder string = new StringBuilder();
        string.append(TAG_START).append(tagName);
        for (String key : parameters.keySet()) {
            string.append(" ").append(key).append("='").append(parameters.get(key)).append("' ");
        }
        string.append(TAG_END);

        string.append(contents.toString());

        string.append(TAG_START_CLOSE).append(tagName).append(TAG_END).toString();

        return string.toString();
    }
}
