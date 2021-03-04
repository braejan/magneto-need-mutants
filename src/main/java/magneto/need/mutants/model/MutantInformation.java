package magneto.need.mutants.model;

import io.reactivex.annotations.NonNull;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Singleton
public class MutantInformation {
    @NonNull
    private List<DnaSequence> sequences;

    @NonNull
    private char[][] dnaMatrix;

    @NonNull
    public List<DnaSequence> getSequences() {
        return sequences;
    }

    public void setSequences(@NonNull List<DnaSequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MutantInformation)) return false;
        MutantInformation that = (MutantInformation) o;
        return sequences.equals(that.sequences) && Arrays.equals(dnaMatrix, that.dnaMatrix);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sequences);
        result = 31 * result + Arrays.hashCode(dnaMatrix);
        return result;
    }

    @NonNull
    public char[][] getDnaMatrix() {
        return dnaMatrix;
    }

    public void setDnaMatrix(@NonNull char[][] dnaMatrix) {
        this.dnaMatrix = dnaMatrix;
    }

    @Override
    public String toString() {
        return "MutantInformation{" +
                "sequences=" + sequences +
                ", dnaMatrix=" + Arrays.toString(dnaMatrix) +
                '}';
    }
}
