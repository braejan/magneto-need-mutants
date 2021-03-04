package magneto.need.mutants.handler;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import magneto.need.mutants.model.ApiResponse;
import magneto.need.mutants.model.Dna;
import magneto.need.mutants.service.DnaHandlerService;
import magneto.need.mutants.validation.Validation;

import javax.inject.Inject;

@Introspected
public class DnaRequestHandler extends MicronautRequestHandler<Dna, ApiResponse> {

    @Inject
    private DnaHandlerService handler;

    @Override
    public ApiResponse execute(Dna input) {
        ApiResponse apiResponse = new ApiResponse();
        final boolean isMutant = handler.isMutant(input.getDna());
        if (isMutant) {
            apiResponse.setStatusCode(200);
        } else {
            apiResponse.setStatusCode(403);
        }
        return apiResponse;
    }
}
