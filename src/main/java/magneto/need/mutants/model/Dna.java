package magneto.need.mutants.model;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Introspected
public class Dna {

    @NonNull
    @NotBlank
    private List<String> dna;

    public Dna() {
    }

    @NonNull
    public List<String> getDna() {
        return dna;
    }

    public void setDna(@NonNull List<String> dna) {
        this.dna = dna;
    }
}
