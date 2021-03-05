package magneto.need.mutants.service.impl;

import magneto.need.mutants.model.DnaSequence;
import magneto.need.mutants.model.MutantInformation;
import magneto.need.mutants.model.Position;
import magneto.need.mutants.service.MutantInformationService;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MutantInformationServiceImpl implements MutantInformationService {
    private final MutantInformation mutantInformation;

    public MutantInformationServiceImpl(MutantInformation mutantInformation) {
        this.mutantInformation = mutantInformation;
    }

    @Override
    public boolean isAFreePosition(Position position) {
        for (DnaSequence sequence : this.mutantInformation.getSequences()) {
            List<Position> positions = sequence.getPositions()
                    .stream()
                    .filter(sequencePosition -> sequencePosition.equals(position))
                    .collect(Collectors.toList());
            if (!positions.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean hasErrors(Position position) {
        if (position == null || this.mutantInformation.getDnaMatrix() == null) {
            return true;
        } else if (!isAFreePosition(position)) {
            return true;
        } else if (this.mutantInformation.getDnaMatrix().length <= position.getX()) {
            return true;
        } else if (this.mutantInformation.getDnaMatrix()[position.getX()].length <= position.getY()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getElementsFromRight(Position position) {
        char[][] dnaMatrix = this.mutantInformation.getDnaMatrix();
        if (hasErrors(position)) {
            return null;
        } else {
            int total = Math.min(position.getY() + 4, dnaMatrix[position.getX()].length);
            StringBuilder builder = new StringBuilder();
            for (int i = position.getY(); i < total; i++) {
                Position validatePosition = new Position();
                validatePosition.setX(position.getX());
                validatePosition.setY(i);
                if (isAFreePosition(validatePosition)) {
                    builder.append(dnaMatrix[position.getX()][i]);
                } else {
                    return null;
                }
            }
            return builder.toString();
        }
    }

    @Override
    public String getElementsFromDiagonalUp(Position position) {
        char[][] dnaMatrix = this.mutantInformation.getDnaMatrix();
        if (hasErrors(position)) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();
            int maxY = Math.min(position.getY() + 4, dnaMatrix.length);
            int minX = Math.max(position.getX() - 4, 0);
            int actualY = position.getY();
            for (int i = position.getX(); i >= minX; i--) {
                Position validatePosition = new Position();
                validatePosition.setX(i);
                validatePosition.setY(actualY);
                if (isAFreePosition(validatePosition)) {
                    builder.append(dnaMatrix[i][actualY]);
                } else {
                    return null;
                }
                actualY++;
                if (actualY == maxY) {
                    break;
                }
            }
            return builder.toString();
        }
    }

    @Override
    public String getElementsFromDiagonalBottom(Position position) {
        char[][] dnaMatrix = this.mutantInformation.getDnaMatrix();
        if (hasErrors(position)) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();
            int maxX = Math.min(position.getX() + 4, dnaMatrix.length);
            int maxY = Math.min(position.getY() + 4, dnaMatrix.length);
            int actualY = position.getY();
            for (int i = position.getX(); i < maxX; i++) {
                Position validatePosition = new Position();
                validatePosition.setX(i);
                validatePosition.setY(actualY);
                if (isAFreePosition(validatePosition)) {
                    builder.append(dnaMatrix[i][actualY]);
                } else {
                    return null;
                }
                actualY++;
                if (actualY == maxY) {
                    break;
                }
            }
            return builder.toString();
        }
    }

    @Override
    public String getElementsFromBottom(Position position) {
        char[][] dnaMatrix = this.mutantInformation.getDnaMatrix();
        if (hasErrors(position)) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();
            int maxX = Math.min(position.getX() + 4, dnaMatrix.length);
            int actualY = position.getY();
            for (int i = position.getX(); i < maxX; i++) {
                Position validatePosition = new Position();
                validatePosition.setX(i);
                validatePosition.setY(actualY);
                if (isAFreePosition(validatePosition)) {
                    builder.append(dnaMatrix[i][actualY]);
                } else {
                    return null;
                }
            }
            return builder.toString();
        }
    }
}
