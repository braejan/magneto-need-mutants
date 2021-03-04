package magneto.need.mutants.dynamodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Singleton
public final class MutantsDb {
    private static final Logger LOGGER = LoggerFactory.getLogger(MutantsDb.class);

    private MutantsDb() {
    }

    public static void saveMutantInformation(List<String> dna) {
        HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();

        // Add all content to the table
        int hash = Objects.hash(dna);
        itemValues.put("mutant-id", AttributeValue.builder().n(String.valueOf(hash)).build());
        itemValues.put("creation-date", AttributeValue.builder().s(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build());
        itemValues.put("dna", AttributeValue.builder().ss(dna).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("mutants")
                .item(itemValues)
                .build();
        Region region = Region.US_WEST_2;
        try (DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build()) {
            ddb.putItem(request);
        } catch (DynamoDbException e) {
            LOGGER.error("Error on saveMutantInformation", e);
        }
    }
}
