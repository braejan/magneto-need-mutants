package magneto.need.mutants;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import magneto.need.mutants.service.DnaHandlerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;

@MicronautTest
public class DnaHandlerServiceTest {

    @Inject
    private DnaHandlerService handler;

    @Test
    public void test_a_real_mutant(){
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        boolean result = handler.isMutant(Arrays.asList(dna));
        Assertions.assertTrue(result);
    }
}
