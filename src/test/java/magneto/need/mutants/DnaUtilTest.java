package magneto.need.mutants;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import magneto.need.mutants.model.DnaSequence;
import magneto.need.mutants.model.Position;
import magneto.need.mutants.util.DnaUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MicronautTest
class DnaUtilTest {
    @Test
    void test_array_conversion_ok() {
        List<String> input = Arrays.asList("ATCG", "TCGA", "CGAT", "GATC");
        char[][] response = DnaUtil.convertDnaList(input);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(4, response.length);
        Assertions.assertEquals(4, response[0].length);
        Assertions.assertEquals(4, response[1].length);
        Assertions.assertEquals(4, response[2].length);
        Assertions.assertEquals(4, response[3].length);
    }

    @Test
    void test_array_conversion_null() {
        char[][] response = DnaUtil.convertDnaList(null);
        Assertions.assertNull(response);
    }

    @Test
    void test_array_conversion_empty() {
        char[][] response = DnaUtil.convertDnaList(new ArrayList<>());
        Assertions.assertNull(response);
    }

    @Test
    void test_create_dna_sequence_right() {
        DnaSequence expected = new DnaSequence();
        expected.setLetter("A");
        expected.setPositions(new ArrayList<>());
        Position position = new Position();
        position.setX(0);
        position.setY(0);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(0);
        position.setY(1);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(0);
        position.setY(2);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(0);
        position.setY(3);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(0);
        position.setY(0);
        DnaSequence result = DnaUtil.createDnaSequence("A", position, DnaUtil.RIGHT_TYPE);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void test_create_dna_sequence_diagonal_up() {
        DnaSequence expected = new DnaSequence();
        expected.setLetter("A");
        expected.setPositions(new ArrayList<>());
        Position position = new Position();
        position.setX(3);
        position.setY(2);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(2);
        position.setY(3);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(1);
        position.setY(4);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(0);
        position.setY(5);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(3);
        position.setY(2);
        DnaSequence result = DnaUtil.createDnaSequence("A", position, DnaUtil.DIAGONAL_UP_TYPE);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void test_create_dna_sequence_diagonal_down() {
        DnaSequence expected = new DnaSequence();
        expected.setLetter("A");
        expected.setPositions(new ArrayList<>());
        Position position = new Position();
        position.setX(0);
        position.setY(2);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(1);
        position.setY(3);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(2);
        position.setY(4);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(3);
        position.setY(5);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(0);
        position.setY(2);
        DnaSequence result = DnaUtil.createDnaSequence("A", position, DnaUtil.DIAGONAL_DOWN_TYPE);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void test_create_dna_sequence_bottom() {
        DnaSequence expected = new DnaSequence();
        expected.setLetter("A");
        expected.setPositions(new ArrayList<>());
        Position position = new Position();
        position.setX(0);
        position.setY(4);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(1);
        position.setY(4);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(2);
        position.setY(4);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(3);
        position.setY(4);
        expected.getPositions().add(position);
        position = new Position();
        position.setX(0);
        position.setY(4);
        DnaSequence result = DnaUtil.createDnaSequence("A", position, DnaUtil.BOTTOM_TYPE);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void test_create_dna_sequence_diagonal_null() {
        DnaSequence expected = new DnaSequence();
        expected.setLetter("A");
        expected.setPositions(new ArrayList<>());
        Position position = new Position();
        position.setX(0);
        position.setY(2);
        DnaSequence result = DnaUtil.createDnaSequence("A", position, -3);
        Assertions.assertNull(result);
        result = DnaUtil.createDnaSequence("A", null, -3);
        Assertions.assertNull(result);
        result = DnaUtil.createDnaSequence(null, position, -3);
        Assertions.assertNull(result);
    }

    @Test
    void test_ratio() {
        int param1 = 40;
        int param2 = 100;
        Assertions.assertEquals(0.4, DnaUtil.ratio(param1, param2));
    }
}
