package magneto.need.mutants;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import magneto.need.mutants.exception.DimensionException;
import magneto.need.mutants.model.Dna;
import magneto.need.mutants.validation.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@MicronautTest
public class ValidationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationTest.class);

    @Test
    public void test_correct_input() {
        try {
            Validation.validate(Arrays.asList("ATGCGA", "CAGTGC", "CAGTGC", "AGAAGG", "CCCCTA", "TCACTG"));
            Assertions.assertTrue(true);
        } catch (DimensionException e) {
            LOGGER.error("Error on test_correct_input", e);
            Assertions.assertTrue(false);
        }
    }

    @Test
    public void test_incorrect_input() throws DimensionException {
        Assertions.assertThrows(DimensionException.class, () -> {
            Validation.validate(Arrays.asList("ABCD", "ACBD", "ADCB", "ADCB", "BBBB"));
        });
    }

    @Test
    public void test_incorrect_input_with_3_letters() throws DimensionException {
        Assertions.assertThrows(DimensionException.class, () -> {
            Validation.validate(Arrays.asList("ABCD", "ACB", "ADCB", "ADB"));
        });
    }

    @Test
    public void test_has_unique_letters() {
        String input = "AAAAAAAAAAA";
        Assertions.assertTrue(Validation.hasUniqueLetter(input));
        input = "AAAAAAAAAAB";
        Assertions.assertFalse(Validation.hasUniqueLetter(input));
    }

    @Test
    public void test_a_mutant_sequence() {
        String input = "AAAA";
        Assertions.assertTrue(Validation.isAMutantSequence(input));
        input = "AAAAAAAAAAB";
        Assertions.assertFalse(Validation.isAMutantSequence(input));
        Assertions.assertFalse(Validation.isAMutantSequence(null));
    }
}
