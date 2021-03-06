package magneto.need.mutants.util;

import magneto.need.mutants.model.DnaSequence;
import magneto.need.mutants.model.Position;

import java.util.ArrayList;
import java.util.List;

public final class DnaUtil {
    public static final int RIGHT_TYPE = 0;
    public static final int DIAGONAL_UP_TYPE = 1;
    public static final int DIAGONAL_DOWN_TYPE = 2;
    public static final int BOTTOM_TYPE = 3;

    private DnaUtil() {
    }

    public static char[][] convertDnaList(List<String> list) {
        final char[][] result;
        if (list != null && !list.isEmpty()) {
            result = new char[list.size()][list.size()];
            for (int i = 0; i < list.size(); i++) {
                result[i] = list.get(i).toCharArray();
            }
        } else {
            result = null;
        }
        return result;
    }

    public static DnaSequence createDnaSequence(String letter, Position initialPosition, int type) {
        DnaSequence sequence = new DnaSequence();
        sequence.setLetter(letter);
        sequence.setPositions(new ArrayList<>());
        if (letter == null || initialPosition == null) {
            return null;
        }
        switch (type) {
            case RIGHT_TYPE:
                for (int i = 0; i < 4; i++) {
                    Position position = new Position();
                    position.setY(initialPosition.getY() + i);
                    position.setX(initialPosition.getX());
                    sequence.getPositions().add(position);
                }
                return sequence;
            case DIAGONAL_UP_TYPE:
                for (int i = 0; i < 4; i++) {
                    Position position = new Position();
                    position.setY(initialPosition.getY() + i);
                    position.setX(initialPosition.getX() - i);
                    sequence.getPositions().add(position);
                }
                return sequence;
            case DIAGONAL_DOWN_TYPE:
                for (int i = 0; i < 4; i++) {
                    Position position = new Position();
                    position.setY(initialPosition.getY() + i);
                    position.setX(initialPosition.getX() + i);
                    sequence.getPositions().add(position);
                }
                return sequence;
            case BOTTOM_TYPE:
                for (int i = 0; i < 4; i++) {
                    Position position = new Position();
                    position.setY(initialPosition.getY());
                    position.setX(initialPosition.getX() + i);
                    sequence.getPositions().add(position);
                }
                return sequence;
            default:
                return null;
        }
    }

    public static double ratio(int param1, int param2) {
        if (param1 < 1 || param2 < 1) {
            return 0;
        }
        return ((double) param1 / (double) param2);
    }
}
