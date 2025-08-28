package co.com.cristiancabarcas.api;

import co.com.cristiancabarcas.api.dtos.CustomResponse;
import co.com.cristiancabarcas.api.dtos.loans.LoanRequest;
import co.com.cristiancabarcas.api.dtos.loans.LoanResponse;
import co.com.cristiancabarcas.api.utils.BuilderResponse;
import co.com.cristiancabarcas.model.loan.Loan;
import co.com.cristiancabarcas.usecase.saveloan.SaveLoanUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/loan")
@RequiredArgsConstructor
public class Handler {

    private final ObjectMapper mapper;
    private final SaveLoanUseCase saveLoanUseCase;
    private static final Logger log = Logger.getLogger(Handler.class.getName());

    @PostMapping
    @Operation(summary = "Create new loan", description = "Registers a new loan in the system")
    public Mono<ResponseEntity<CustomResponse<LoanResponse>>> createNewLoan(@RequestBody LoanRequest loanRequest) {

        log.info("::::: INIT CREATE LOAN :::::");
        Loan request = mapper.map(loanRequest, Loan.class);

        return saveLoanUseCase.execute(request, loanRequest.getUserId(), loanRequest.getLoanType())
                .doOnNext(loan -> log.info("Loan created success for amount: " + loan.getAmount()))
                .map(loan -> {
                    LoanResponse result = mapper.map(loan, LoanResponse.class);
                    result.setLoanType(loan.getLoanType().getDescription());
                    result.setStatus(loan.getStatus().getDescription());
                    return result;
                })
                .map(BuilderResponse::buildCreatedLoan);
    }

}
