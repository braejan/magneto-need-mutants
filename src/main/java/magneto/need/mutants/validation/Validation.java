package magneto.need.mutants.validation;

import jdk.internal.joptsimple.internal.Strings;
import magneto.need.mutants.exception.DimensionException;
import magneto.need.mutants.model.Dna;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Validation {
    private Validation() {
    }

    public static void validate(Dna input) throws DimensionException {
        if (input == null || input.getDna() == null) {
            throw new DimensionException("Input must be not null.");
        }
        if (input.getDna().size() == 0) {
            throw new DimensionException("Input must be not null.");
        }
        int total = input.getDna().size();
        List<String> finalItems = input.getDna().stream()
                .filter(item -> item == null || item.length() != total || !item.matches("[A|T|C|G]{4}"))
                .collect(Collectors.toList());
        if (finalItems != null && finalItems.size() > 0){
            throw new DimensionException("All items should be had a length of " + total);
        }
    }
}
