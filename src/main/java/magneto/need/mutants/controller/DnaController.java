package magneto.need.mutants.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import magneto.need.mutants.dynamodb.MutantsDb;
import magneto.need.mutants.exception.DimensionException;
import magneto.need.mutants.model.Dna;
import magneto.need.mutants.model.Stat;
import magneto.need.mutants.service.DnaHandlerService;
import magneto.need.mutants.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
public class DnaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DnaController.class);
    @Inject
    private DnaHandlerService dnaHandlerService;
    @Inject
    private MutantsDb mutantsDb;

    @Post("/mutant/")
    public HttpResponse checkDna(@Valid @Body Dna dna) {
        LOGGER.info("Starting request with {}", dna);
        final boolean isMutant = dnaHandlerService.isMutant(dna.getDna());
        if (isMutant) {
            return HttpResponse.ok();
        } else {
            return HttpResponse.badRequest().status(403);
        }
    }

    @Get("/stats/")
    public Stat stats() {
        return dnaHandlerService.getStatistics();
    }
}
