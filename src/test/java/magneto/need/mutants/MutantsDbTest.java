package magneto.need.mutants;

import magneto.need.mutants.dynamodb.MutantsDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.exception.SdkClientException;

class MutantsDbTest {
    @Test
    void test_error_dynamo_updating_statistics() {
        Assertions.assertThrows(SdkClientException.class, () -> {
            MutantsDb.updateStatistics(false);
        });
    }
}
