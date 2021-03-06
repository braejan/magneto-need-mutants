package magneto.need.mutants.dynamodb;

import magneto.need.mutants.model.Stat;
import magneto.need.mutants.util.DnaUtil;
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

    public void saveMutantInformation(List<String> dna, boolean isMutant, DynamoDbClient ddb) {
        HashMap<String, AttributeValue> itemValues = new HashMap<>();
        // Add all content to the table
        int hash = Objects.hash(dna);
        if (existsId(hash, ddb)) {
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
        try {
            ddb.putItem(request);
            updateStatistics(isMutant, ddb);
        } catch (DynamoDbException e) {
            LOGGER.error("Error on saveMutantInformation", e);
        }
    }

    public GetItemResponse getStatisticsFromDynamo(DynamoDbClient ddb) {
        GetItemResponse response;
        HashMap<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put("Process", AttributeValue.builder()
                .s("mutantAnalysis").build());
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("processcontrol")
                .key(keyToGet)
                .build();
        try {
            response = ddb.getItem(getItemRequest);
        } catch (Exception e) {
            LOGGER.error("Error on getStatisticsFromDynamo", e);
            response = null;
        }
        return response;
    }

    public void updateStatistics(boolean isMutant, DynamoDbClient ddb) {
        try {
            GetItemResponse response = getStatisticsFromDynamo(ddb);
            int mutantCount = 0;
            int humanCount = 0;
            if (response == null) {
                return;
            }
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
            HashMap<String, AttributeValue> keyToGet = new HashMap<>();
            keyToGet.put("Process", AttributeValue.builder()
                    .s("mutantAnalysis").build());
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

    public boolean existsId(int hash, DynamoDbClient ddb) {
        HashMap<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put("MutantId", AttributeValue.builder()
                .n(String.valueOf(hash)).build());
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("mutants")
                .key(keyToGet)
                .build();
        try {
            GetItemResponse response = ddb.getItem(getItemRequest);
            return response.item().get("MutantId") != null;
        } catch (DynamoDbException e) {
            LOGGER.error("Error on existsId", e);
        }
        return true;
    }

    public Stat getStatistics(DynamoDbClient ddb) {
        GetItemResponse response = getStatisticsFromDynamo(ddb);
        Integer mutantCount = Integer.parseInt(response.item().get("MutantCount").n());
        Integer humanCount = Integer.parseInt(response.item().get("HumanCount").n());
        Stat stat = new Stat();
        stat.setCountMutantDna(mutantCount);
        stat.setCountHumanDna(humanCount);
        stat.setRatio(DnaUtil.ratio(mutantCount, humanCount));
        return stat;
    }
}
