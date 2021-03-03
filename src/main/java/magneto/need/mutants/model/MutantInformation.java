package magneto.need.mutants.model;

import io.reactivex.annotations.NonNull;

import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;

@Singleton
public class MutantInformation {
    @NonNull
    List<DnaSequence> sequences;

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
        return sequences.equals(that.sequences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequences);
    }

    @Override
    public String toString() {
        return "MutantInformation{" +
                "sequences=" + sequences +
                '}';
    }
}
