package magneto.need.mutants.util;

import java.util.List;

public final class DnaUtil {
    private DnaUtil() {
    }

    public static char[][] convertDnaList(List<String> list) {
        final char[][] result;
        if (list != null && list.size() > 0) {
            result = new char[list.size()][list.size()];
            for (int i = 0; i < list.size(); i++) {
                result[i] = list.get(i).toCharArray();
            }
        } else {
            result = null;
        }
        return result;
    }
}
