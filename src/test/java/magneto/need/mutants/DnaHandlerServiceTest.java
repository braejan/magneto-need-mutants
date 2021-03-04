package magneto.need.mutants;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import magneto.need.mutants.service.DnaHandlerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;

@MicronautTest
class DnaHandlerServiceTest {

    @Inject
    private DnaHandlerService handler;

    @Test
    void test_a_real_mutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        boolean result = handler.isMutant(Arrays.asList(dna));
        Assertions.assertTrue(result);
    }

    @Test
    void test_b_real_mutant() {
        String[] dna = {"ATGCTGCTAC", "TGTTCTGTTT", "GAATCCCTAT", "CGGGTCCCCG", "ATGCTGGAAC", "GGTGAAGGCG", "TTGGAATTCG", "CCGCAGCCTC", "GGTGACGGAG", "GTATTCTTGT"};
        boolean result = handler.isMutant(Arrays.asList(dna));
        Assertions.assertTrue(result);
    }
    @Test
    void test_a_non_mutant() {
        String[] dna = {"ATGCTGCTAC","TGTTCTGTTT","GAAACGCTAT","CGGGTCCACG","ATGCTGGAAC","GGTCAAGGCG","TTGGAATTCG","CCGCAGCCTC","GCTGACGGAG","GTATTCTTGT"};
        boolean result = handler.isMutant(Arrays.asList(dna));
        Assertions.assertFalse(result);
    }

    @Test
    void test_another_mutant() {
        String[] dna = {"ATGCTGCTAC", "TGTTCTGTTT", "GAATCCTTAT", "CGGGTTGCCG", "ATGCTGGAAC", "GGTTAAGGCG", "TTGGAACTCG", "CCGCGGCCTC", "GGTGTCGGCG", "GTTTTCTTGC"};
        boolean result = handler.isMutant(Arrays.asList(dna));
        Assertions.assertTrue(result);
    }

}
