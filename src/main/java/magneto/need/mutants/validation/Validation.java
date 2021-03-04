package magneto.need.mutants.validation;

import magneto.need.mutants.exception.DimensionException;

import java.util.List;
import java.util.stream.Collectors;

public final class Validation {
    private Validation() {
    }

    public static void validate(List<String> input) throws DimensionException {
        if (input.isEmpty()) {
            throw new DimensionException("Input must be not null.");
        }
        final int total = input.size();
        List<String> finalItems = input.stream()
                .filter(item -> item == null || item.length() != total || !item.matches("[A|T|C|G]+"))
                .collect(Collectors.toList());
        if (!finalItems.isEmpty()) {
            throw new DimensionException("All items should be had a length of " + total);
        }
    }

    public static boolean hasUniqueLetter(String input) {
        String result = input.chars().distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
        return result.length() == 1;
    }

    public static boolean isAMutantSequence(String input) {
        return input != null &&
                input.length() == 4 &&
                Validation.hasUniqueLetter(input);
    }
}
