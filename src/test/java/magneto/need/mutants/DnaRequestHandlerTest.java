package magneto.need.mutants;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import magneto.need.mutants.handler.DnaRequestHandler;
import magneto.need.mutants.model.ApiResponse;
import magneto.need.mutants.model.Dna;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class DnaRequestHandlerTest {

    private static magneto.need.mutants.handler.DnaRequestHandler DnaRequestHandler;

    @BeforeAll
    public static void setupServer() {
        DnaRequestHandler = new DnaRequestHandler();
    }

    @AfterAll
    public static void stopServer() {
        if (DnaRequestHandler != null) {
            DnaRequestHandler.getApplicationContext().close();
        }
    }

    @Test
    void test_handler_ok() {
        Dna dna = new Dna();
        dna.setDna(Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"));
        ApiResponse apiResponse = DnaRequestHandler.execute(dna);
        assertNotNull(apiResponse);
        assertEquals(200, apiResponse.getStatusCode());
    }

    @Test
    void test_handler_error() {
        Dna dna = new Dna();
        dna.setDna(Arrays.asList("BDA", "TCGA", "CGAT", "GATC"));
        ApiResponse apiResponse = DnaRequestHandler.execute(dna);
        assertNotNull(apiResponse);
        assertEquals(403, apiResponse.getStatusCode());
    }
}
