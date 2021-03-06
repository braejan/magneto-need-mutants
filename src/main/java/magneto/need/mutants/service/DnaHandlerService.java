package magneto.need.mutants.service;

import magneto.need.mutants.model.Stat;

import java.util.List;

public interface DnaHandlerService {
    public boolean isMutant(List<String> dna);

    public Stat getStatistics();
}
