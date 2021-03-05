package magneto.need.mutants.dynamodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Singleton
public class MutantsDb {
    private static final Logger LOGGER = LoggerFactory.getLogger(MutantsDb.class);

    public static void saveMutantInformation(List<String> dna, boolean isMutant) {
        HashMap<String, AttributeValue> itemValues = new HashMap<>();
        // Add all content to the table
        int hash = Objects.hash(dna);
        if (existsId(hash)) {
            LOGGER.info("Id {} already exists.", hash);
            return;
        }
        LOGGER.info("Saving MutantId {}", hash);
        itemValues.put("MutantId", AttributeValue.builder().n(String.valueOf(hash)).build());
        itemValues.put("CreationDate", AttributeValue.builder().s(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build());
        itemValues.put("DnaSequence", AttributeValue.builder().ss(dna).build());
        itemValues.put("IsMutant", AttributeValue.builder().bool(isMutant).build());
        PutItemRequest request = PutItemRequest.builder()
                .tableName("mutants")
                .item(itemValues)
                .build();
        Region region = Region.US_EAST_2;
        try (DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build()) {
            ddb.putItem(request);
            updateStatistics(isMutant);
        } catch (DynamoDbException e) {
            LOGGER.error("Error on saveMutantInformation", e);
        }
    }

    private static void updateStatistics(boolean isMutant) {
        Region region = Region.US_EAST_2;
        HashMap<String, AttributeValue> keyToGet = new HashMap<>();

        keyToGet.put("Process", AttributeValue.builder()
                .s("mutantAnalysis").build());
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("processcontrol")
                .key(keyToGet)
                .build();
        try (DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build()) {
            GetItemResponse response = ddb.getItem(getItemRequest);
            int mutantCount = 0;
            int humanCount = 0;
            if (response.item().get("Process") != null) {
                mutantCount = Integer.parseInt(response.item().get("MutantCount").n());
                humanCount = Integer.parseInt(response.item().get("HumanCount").n());
            }
            if (isMutant) {
                mutantCount += 1;
            } else {
                humanCount += 1;
            }
            HashMap<String, AttributeValueUpdate> updatedValues =
                    new HashMap<>();
            updatedValues.put("MutantCount", AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().n(String.valueOf(mutantCount)).build())
                    .action(AttributeAction.PUT)
                    .build());
            updatedValues.put("HumanCount", AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().n(String.valueOf(humanCount)).build())
                    .action(AttributeAction.PUT)
                    .build());
            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                    .tableName("processcontrol")
                    .key(keyToGet)
                    .attributeUpdates(updatedValues)
                    .build();
            ddb.updateItem(updateItemRequest);

        } catch (DynamoDbException e) {
            LOGGER.error("Error on updateStatistics", e);
        }
    }

    private static boolean existsId(int hash) {
        Region region = Region.US_EAST_2;
        HashMap<String, AttributeValue> keyToGet = new HashMap<>();

        keyToGet.put("MutantId", AttributeValue.builder()
                .n(String.valueOf(hash)).build());
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("mutants")
                .key(keyToGet)
                .build();
        try (DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build()) {
            GetItemResponse response = ddb.getItem(getItemRequest);
            return response.item().get("MutantId") != null;
        } catch (DynamoDbException e) {
            LOGGER.error("Error on existsId", e);
        }
        return true;
    }
}
