package magneto.need.mutants;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import magneto.need.mutants.model.ApiResponse;
import magneto.need.mutants.model.Dna;
import magneto.need.mutants.validation.Validation;

import java.util.UUID;

@Introspected
public class DnaRequestHandler extends MicronautRequestHandler<Dna, ApiResponse> {

    @Override
    public ApiResponse execute(Dna input) {
        ApiResponse apiResponse = new ApiResponse();
        try{
            Validation.validate(input);
            apiResponse.setStatusCode(200);
        }catch (Exception e){
            apiResponse.setStatusCode(403);
        }
        return apiResponse;
    }
}
