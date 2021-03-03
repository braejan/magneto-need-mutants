package magneto.need.mutants;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import magneto.need.mutants.util.DnaUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MicronautTest
public class DnaUtilTest {
    @Test
    public void test_array_conversion_ok(){
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
    public void test_array_conversion_null(){
        char[][] response = DnaUtil.convertDnaList(null);
        Assertions.assertNull(response);
    }

    @Test
    public void test_array_conversion_empty(){
        char[][] response = DnaUtil.convertDnaList(new ArrayList<>());
        Assertions.assertNull(response);
    }
}
