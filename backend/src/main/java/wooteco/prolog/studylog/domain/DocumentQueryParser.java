package wooteco.prolog.studylog.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DocumentQueryParser {

    private static final String WILD_CARD = "*";
    private static final String OR = " OR ";
    private static final String EMPTY = " ";

    public static List<String> removeSpecialChars(List<String> words) {
        List<String> results = new ArrayList<>();
        for (String word : words) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&' || c == '/') {
                    continue;
                }
                stringBuilder.append(c);
            }
            String string = stringBuilder.toString();
            if (!Objects.equals(string, "")) {
                results.add(string);
            }
        }

        if (results.isEmpty()) {
            results.add(EMPTY);
        }
        return results;
    }

    public static String makeDefaultQueryString(List<String> inputs) {
        if (Objects.isNull(inputs) || inputs.isEmpty()) {
            return WILD_CARD;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputs.size(); i++) {
            sb.append(inputs.get(i));
            if (i != inputs.size() - 1) {
                sb.append(OR);
            }
        }
        return sb.toString();
    }

    public static String makeKeywordsQueryString(List<String> keywords) {
        if (Objects.isNull(keywords) || keywords.isEmpty()) {
            return WILD_CARD;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            final String value = keywords.get(i);

            if (value.contains(EMPTY)) {
                sb.append("\"*").append(value).append("*\"");
            } else {
                sb.append(WILD_CARD).append(value).append(WILD_CARD);
            }

            if (i != keywords.size() - 1) {
                sb.append(OR);
            }
        }
        return sb.toString();
    }
}
