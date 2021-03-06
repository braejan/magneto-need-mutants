package magneto.need.mutants.service.impl;

import io.micronaut.http.HttpResponse;
import magneto.need.mutants.dynamodb.MutantsDb;
import magneto.need.mutants.exception.DimensionException;
import magneto.need.mutants.model.MutantInformation;
import magneto.need.mutants.model.Position;
import magneto.need.mutants.model.Stat;
import magneto.need.mutants.service.DnaHandlerService;
import magneto.need.mutants.service.MutantInformationService;
import magneto.need.mutants.util.DnaUtil;
import magneto.need.mutants.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class DnaHandlerServiceImpl implements DnaHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DnaHandlerServiceImpl.class);
    private final MutantInformation mutantInformation;
    private final MutantInformationService mutantInformationService;
    private final MutantsDb mutantsDb;
    private DynamoDbClient ddb;

    public DnaHandlerServiceImpl(MutantInformation mutantInformation, MutantInformationService mutantInformationService, MutantsDb mutantsDb) {
        this.mutantInformation = mutantInformation;
        this.mutantInformationService = mutantInformationService;
        this.mutantsDb = mutantsDb;
        Region region = Region.US_EAST_2;
        ddb = DynamoDbClient.builder().region(region).build();
    }

    private boolean hasMutantDnaSequence() {
        return this.mutantInformation.getSequences().size() == 3;
    }

    private void addDnaSequence(int type, Position position, String result) {
        this.mutantInformation
                .getSequences()
                .add(DnaUtil.createDnaSequence(result.substring(0, 1)
                        , position
                        , type)
                );
    }

    @Override
    public boolean isMutant(List<String> dna) {
        try {
            Validation.validate(dna);
        } catch (DimensionException e) {
            return false;
        }
        this.mutantInformation.setSequences(new ArrayList<>());
        this.mutantInformation.setDnaMatrix(DnaUtil.convertDnaList(dna));
        char[][] matrix = this.mutantInformation.getDnaMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Position position = new Position();
                position.setX(i);
                position.setY(j);
                String resultRight = this.mutantInformationService.getElementsFromRight(position);
                if (Validation.isAMutantSequence(resultRight)) {
                    addDnaSequence(DnaUtil.RIGHT_TYPE, position, resultRight);
                    if (hasMutantDnaSequence()) {
                        saveOnDynamoDb(ddb, dna, true);
                        return true;
                    }
                }
                String resultDiagonalUp = this.mutantInformationService.getElementsFromDiagonalUp(position);
                if (Validation.isAMutantSequence(resultDiagonalUp)) {
                    addDnaSequence(DnaUtil.DIAGONAL_UP_TYPE, position, resultDiagonalUp);
                    if (hasMutantDnaSequence()) {
                        saveOnDynamoDb(ddb, dna, true);
                        return true;
                    }
                }
                String resultDiagonalDown = this.mutantInformationService.getElementsFromDiagonalBottom(position);
                if (Validation.isAMutantSequence(resultDiagonalDown)) {
                    addDnaSequence(DnaUtil.DIAGONAL_DOWN_TYPE, position, resultDiagonalDown);
                    if (hasMutantDnaSequence()) {
                        saveOnDynamoDb(ddb, dna, true);
                        return true;
                    }
                }
                String resultDiagonalBottom = this.mutantInformationService.getElementsFromBottom(position);
                if (Validation.isAMutantSequence(resultDiagonalBottom)) {
                    addDnaSequence(DnaUtil.DIAGONAL_DOWN_TYPE, position, resultDiagonalBottom);
                    if (hasMutantDnaSequence()) {
                        saveOnDynamoDb(ddb, dna, true);
                        return true;
                    }
                }
            }
        }
        saveOnDynamoDb(ddb, dna, hasMutantDnaSequence());
        return hasMutantDnaSequence();
    }

    @Override
    public Stat getStatistics() {
        return this.mutantsDb.getStatistics(ddb);
    }

    private void saveOnDynamoDb(DynamoDbClient ddb, List<String> dna, boolean isMutant) {
        try {
            this.mutantsDb.saveMutantInformation(dna, hasMutantDnaSequence(), ddb);
        } catch (Exception e) {
            LOGGER.error("Error on saveOnDynamoDb", e);
        }
    }
}
