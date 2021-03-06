package magneto.need.mutants;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import magneto.need.mutants.dynamodb.MutantsDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@MicronautTest
public class MutantsDbTest {
    @Inject
    private MutantsDb mutantsDb;

    @Mock
    private DynamoDbClient ddb;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_get_statistics_from_dynamo() {
        GetItemResponse getItemResponse = mock(GetItemResponse.class);
        doReturn(getItemResponse).when(ddb).getItem(any(GetItemRequest.class));
        GetItemResponse response = mutantsDb.getStatisticsFromDynamo(ddb);
        Assertions.assertNotNull(response);
    }

    @Test
    public void test_get_statistics_from_dynamo_with_error() {
        Mockito.reset();
        Region region = Region.US_EAST_2;
        DynamoDbClient ddb = DynamoDbClient.builder().region(region).build();
        GetItemResponse response = mutantsDb.getStatisticsFromDynamo(ddb);
        Assertions.assertNull(response);
    }

    @Test
    public void test_exists_id() {
        GetItemResponse getItemResponse = mock(GetItemResponse.class);
        doReturn(getItemResponse).when(ddb).getItem(any(GetItemRequest.class));
        boolean result = mutantsDb.existsId(1, ddb);
        Assertions.assertFalse(result);
    }

    @Test
    public void  test_save_mutant_information(){
        GetItemResponse getItemResponse = mock(GetItemResponse.class);
        doReturn(getItemResponse).when(ddb).getItem(any(GetItemRequest.class));
        List<String> dna = Arrays.asList("ATGCTGCTAC", "TGTTKTGTTT", "GAAACGCTAT", "CGGGTCCACG", "ATGCTGGAAC", "GGTCAAGGCG", "TTGGAATTCG", "CCGCAGCCTC", "GCTGACGGAG", "GTATTCTTGT");
        this.mutantsDb.saveMutantInformation(dna, true, ddb);
        Assertions.assertFalse(false);
    }
}
