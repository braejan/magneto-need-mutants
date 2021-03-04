package magneto.need.mutants.service.impl;

import magneto.need.mutants.dynamodb.MutantsDb;
import magneto.need.mutants.exception.DimensionException;
import magneto.need.mutants.model.MutantInformation;
import magneto.need.mutants.model.Position;
import magneto.need.mutants.service.DnaHandlerService;
import magneto.need.mutants.service.MutantInformationService;
import magneto.need.mutants.util.DnaUtil;
import magneto.need.mutants.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class DnaHandlerServiceImpl implements DnaHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DnaHandlerServiceImpl.class);
    private final MutantInformation mutantInformation;
    private final MutantInformationService mutantInformationService;

    public DnaHandlerServiceImpl(MutantInformation mutantInformation, MutantInformationService mutantInformationService) {
        this.mutantInformation = mutantInformation;
        this.mutantInformationService = mutantInformationService;
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
        //Validate dna
        try {
            Validation.validate(dna);
        } catch (DimensionException e) {
            LOGGER.error("Error on isMutant", e);
            return false;
        }
        //Initialize mutantInformation
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
                        return true;
                    }
                }
                String resultDiagonalUp = this.mutantInformationService.getElementsFromDiagonalUp(position);
                if (Validation.isAMutantSequence(resultDiagonalUp)) {
                    addDnaSequence(DnaUtil.DIAGONAL_UP_TYPE, position, resultDiagonalUp);
                    if (hasMutantDnaSequence()) {
                        return true;
                    }
                }
                String resultDiagonalDown = this.mutantInformationService.getElementsFromDiagonalBottom(position);
                if (Validation.isAMutantSequence(resultDiagonalDown)) {
                    addDnaSequence(DnaUtil.DIAGONAL_DOWN_TYPE, position, resultDiagonalDown);
                    if (hasMutantDnaSequence()) {
                        return true;
                    }
                }
                String resultDiagonalBottom = this.mutantInformationService.getElementsFromBottom(position);
                if (Validation.isAMutantSequence(resultDiagonalBottom)) {
                    addDnaSequence(DnaUtil.DIAGONAL_DOWN_TYPE, position, resultDiagonalBottom);
                    if (hasMutantDnaSequence()) {
                        return true;
                    }
                }
            }
        }
        if (hasMutantDnaSequence()) {
            MutantsDb.saveMutantInformation(dna);
        }
        return hasMutantDnaSequence();
    }
}
