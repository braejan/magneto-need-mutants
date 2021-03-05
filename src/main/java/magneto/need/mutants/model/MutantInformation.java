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

    @NonNull
    public char[][] getDnaMatrix() {
        return dnaMatrix;
    }

    public void setDnaMatrix(@NonNull char[][] dnaMatrix) {
        this.dnaMatrix = dnaMatrix;
    }
}
