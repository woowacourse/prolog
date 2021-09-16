package wooteco.support.number;

public class NumberUtils {

    public static boolean isNumeric(String string) {
        if (string != null && string.length() != 0) {
            int l = string.length();

            for (int i = 0; i < l; ++i) {
                if (!Character.isDigit(string.codePointAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
