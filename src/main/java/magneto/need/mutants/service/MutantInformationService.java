package magneto.need.mutants.service;

import magneto.need.mutants.model.DnaSequence;
import magneto.need.mutants.model.Position;

public interface MutantInformationService {
    public void updateMutantInformation(DnaSequence sequence);
    public boolean isAFreePosition(Position position);
    public String getElementsFromRight(Position position);
}
