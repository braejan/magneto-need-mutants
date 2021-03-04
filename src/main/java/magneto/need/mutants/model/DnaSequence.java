package magneto.need.mutants.model;

import io.micronaut.core.annotation.Introspected;
import io.reactivex.annotations.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Introspected
public class DnaSequence {
    @NonNull
    @NotBlank
    private String letter;

    @NonNull
    private List<Position> positions;

    @NonNull
    public String getLetter() {
        return letter;
    }

    public void setLetter(@NonNull String letter) {
        this.letter = letter;
    }

    @NonNull
    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(@NonNull List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DnaSequence)) return false;
        DnaSequence that = (DnaSequence) o;
        return letter.equals(that.letter) && positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, positions);
    }

    @Override
    public String toString() {
        return "DnaSequence{" +
                "letter='" + letter + '\'' +
                ", positions=" + positions +
                '}';
    }
}
