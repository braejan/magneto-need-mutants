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
        Dna dna = new Dna();
        dna.setDna(Arrays.asList("ATCG", "TCGA", "CGAT", "GATC"));
        try {
            Validation.validate(dna);
            Assertions.assertTrue(true);
        } catch (DimensionException e) {
            LOGGER.error("Error on test_correct_input", e);
            Assertions.assertTrue(false);
        }
    }

    @Test
    public void test_incorrect_input() throws DimensionException {
        Assertions.assertThrows(DimensionException.class, () -> {
            Dna dna = new Dna();
            dna.setDna(Arrays.asList("ABCD", "ACBD", "ADCB", "ADCB", "BBBB"));
            Validation.validate(dna);
        });
    }

    @Test
    public void test_incorrect_input_with_3_letters() throws DimensionException {
        Assertions.assertThrows(DimensionException.class, () -> {
            Dna dna = new Dna();
            dna.setDna(Arrays.asList("ABCD", "ACB", "ADCB", "ADB"));
            Validation.validate(dna);
        });
    }
}
