package magneto.need.mutants.service.impl;

import magneto.need.mutants.model.DnaSequence;
import magneto.need.mutants.model.MutantInformation;
import magneto.need.mutants.model.Position;
import magneto.need.mutants.service.MutantInformationService;

import javax.inject.Singleton;
import javax.sound.midi.Sequence;
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
    public void updateMutantInformation(DnaSequence sequence) {
        if (this.mutantInformation.getSequences() == null) {
            this.mutantInformation.setSequences(new ArrayList<>());
        }
        this.mutantInformation.getSequences().add(sequence);
    }

    @Override
    public boolean isAFreePosition(Position position) {
        if(this.mutantInformation.getSequences() == null){
            return true;
        }
        for (DnaSequence sequence : this.mutantInformation.getSequences()) {
            List<Position> positions = sequence.getPositions()
                    .stream()
                    .filter(sequencePosition -> sequencePosition.equals(position))
                    .collect(Collectors.toList());
            if (positions != null && positions.size() > 0) {
                return false;
            }
        }
        return true;
    }
}
