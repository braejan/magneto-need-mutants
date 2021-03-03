package magneto.need.mutants;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import magneto.need.mutants.model.DnaSequence;
import magneto.need.mutants.model.MutantInformation;
import magneto.need.mutants.model.Position;
import magneto.need.mutants.service.MutantInformationService;
import magneto.need.mutants.util.DnaUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MicronautTest
public class MutantInformationServiceTest {
    @Inject
    private MutantInformationService mutantInformationService;

    @Inject
    private MutantInformation mutantInformation;

    private void init() {
        DnaSequence sequence = new DnaSequence();
        sequence.setLetter("A");
        sequence.setPositions(new ArrayList<>());
        for (int i = 0; i < 4; i++) {
            Position position = new Position();
            position.setX(0);
            position.setY(i);
            sequence.getPositions().add(position);
        }
        mutantInformationService.updateMutantInformation(sequence);
        List<String> dnaList = Arrays.asList("ATCGA", "TCGAT", "CGATC", "GATCG", "GCTAG");
        mutantInformation.setDnaMatrix(DnaUtil.convertDnaList(dnaList));
    }

    @Test
    public void test_a_update_mutant_information() {
        this.mutantInformation.setSequences(null);
        init();
        Assertions.assertEquals(1, mutantInformation.getSequences().size());
        Assertions.assertEquals(4, mutantInformation.getSequences().get(0).getPositions().size());
    }

    @Test
    public void test_b_free_position() {
        if (this.mutantInformation.getSequences() == null) {
            init();
        }
        Position position = new Position();
        position.setX(0);
        position.setY(1);
        boolean result = mutantInformationService.isAFreePosition(position);
        Assertions.assertFalse(result);
        position.setX(1);
        position.setY(0);
        result = mutantInformationService.isAFreePosition(position);
        Assertions.assertTrue(result);
    }

    @Test
    public void test_get_right_elements(){
        if (this.mutantInformation.getSequences() == null) {
            init();
        }
        /* Example
         *    0 1 2 3 4
         * 0 |A|T|C|G|A|
         * 1 |T|C|G|A|T|
         * 2 |C|G|A|T|C|
         * 3 |G|A|T|C|G|
         * 4 |G|C|T|A|G|
         */
        Position position = new Position();
        position.setX(2);
        position.setY(1);
        // [2,1] = GATC
        String result = this.mutantInformationService.getElementsFromRight(position);
        Assertions.assertEquals("GATC", result);
        position.setX(3);
        position.setY(4);
        // [2,1] = G
        result = this.mutantInformationService.getElementsFromRight(position);
        Assertions.assertEquals("G", result);
        position.setX(0);
        position.setY(0);
        // [0,0] = ATCG
        result = this.mutantInformationService.getElementsFromRight(position);
        Assertions.assertEquals("ATCG", result);
        position.setX(5);
        result = this.mutantInformationService.getElementsFromRight(position);
        Assertions.assertNull(result);
        position.setX(0);
        position.setY(5);
        result = this.mutantInformationService.getElementsFromRight(position);
        Assertions.assertNull(result);

    }
}
