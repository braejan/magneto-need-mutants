package magneto.need.mutants;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import magneto.need.mutants.model.DnaSequence;
import magneto.need.mutants.model.MutantInformation;
import magneto.need.mutants.model.Position;
import magneto.need.mutants.service.MutantInformationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;

@MicronautTest
public class MutantInformationServiceTest {
    @Inject
    private MutantInformationService mutantInformationService;

    @Inject
    private MutantInformation mutantInformation;

    @Test
    public void test_a_update_mutant_information() {
        this.mutantInformation.setSequences(null);
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
        Assertions.assertEquals(1, mutantInformation.getSequences().size());
        Assertions.assertEquals(4, mutantInformation.getSequences().get(0).getPositions().size());
    }

    @Test
    public void test_b_free_position() {
        if (this.mutantInformation.getSequences() == null) {
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
}
