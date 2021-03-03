package magneto.need.mutants.validation;

import jdk.internal.joptsimple.internal.Strings;
import magneto.need.mutants.exception.DimensionException;
import magneto.need.mutants.model.Dna;

import java.util.List;
import java.util.stream.Collectors;

public final class Validation {
    private Validation() {
    }

    public static void validate(List<String> input) throws DimensionException {
        if (input.size() == 0) {
            throw new DimensionException("Input must be not null.");
        }
        final int total = input.size();
        List<String> finalItems = input.stream()
                .filter(item -> item == null || item.length() != total || !item.matches("[A|T|C|G]+"))
                .collect(Collectors.toList());
        if (finalItems != null && finalItems.size() > 0) {
            throw new DimensionException("All items should be had a length of " + total);
        }
    }

    public static boolean hasUniqueLetter(String input) {
        String result = input.chars().distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
        if (result.length() == 1) {
            return true;
        }
        return false;
    }

    public static boolean isAMutantSequence(String input) {
        boolean result = input != null &&
                input.length() == 4 &&
                Validation.hasUniqueLetter(input);
        return result;
    }
}
