package magneto.need.mutants.model;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Introspected
public class Dna {

    @NonNull
    @NotBlank
    private List<String> dna;

    @NonNull
    public List<String> getDna() {
        return dna;
    }

    public void setDna(@NonNull List<String> dna) {
        this.dna = dna;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dna)) return false;
        Dna dna1 = (Dna) o;
        return dna.equals(dna1.dna);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dna);
    }

    @Override
    public String toString() {
        return "Dna{" +
                "dna=" + dna +
                '}';
    }
}
