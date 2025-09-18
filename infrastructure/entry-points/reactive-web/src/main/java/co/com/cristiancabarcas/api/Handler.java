package co.com.cristiancabarcas.api;

import co.com.cristiancabarcas.api.dtos.CustomResponse;
import co.com.cristiancabarcas.api.dtos.loans.LoanRequest;
import co.com.cristiancabarcas.api.dtos.loans.LoanResponse;
import co.com.cristiancabarcas.api.dtos.loans.RevisionLoanResponseItem;
import co.com.cristiancabarcas.api.gateways.JwtAuthRepository;
import co.com.cristiancabarcas.api.utils.BuilderResponse;
import co.com.cristiancabarcas.model.loan.Loan;
import co.com.cristiancabarcas.usecase.listreviewloans.ListReviewLoansUseCase;
import co.com.cristiancabarcas.usecase.saveloan.SaveLoanUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class Handler {

    private final ObjectMapper mapper;
    private final JwtAuthRepository jwtAuthRepository;
    private final SaveLoanUseCase saveLoanUseCase;
    private final ListReviewLoansUseCase listReviewLoansUseCase;
    private static final Logger log = Logger.getLogger(Handler.class.getName());

    @PostMapping("/loan")
    @Operation(summary = "Create new loan", description = "Registers a new loan in the system")
    public Mono<ResponseEntity<CustomResponse<LoanResponse>>> createNewLoan(@RequestBody LoanRequest loanRequest,
                                                                            @RequestHeader("Authorization") String token){

        log.info("::::: INIT CREATE LOAN :::::");
        Loan request = mapper.map(loanRequest, Loan.class);

        return jwtAuthRepository.getUserName(token)
                .flatMap(userId -> saveLoanUseCase.execute(request, loanRequest.getUserId(), loanRequest.getLoanType(), userId))
                .doOnNext(loan -> log.info("Loan created success for amount: " + loan.getAmount()))
                .map(loan -> {
                    LoanResponse result = mapper.map(loan, LoanResponse.class);
                    result.setLoanType(loan.getLoanType().getDescription());
                    result.setStatus(loan.getStatus().getDescription());
                    return result;
                })
                .map(BuilderResponse::buildCreatedLoan);
    }

    @GetMapping("/loans")
    @Operation(summary = "List loans for revision", description = "Lists all loans that are pending revision")
    public Mono<ResponseEntity<CustomResponse<List<RevisionLoanResponseItem>>>> listLoansForRevision() {
        return listReviewLoansUseCase.execute(Map.of())
                .doOnNext(loans -> log.info("Number of loans for review: " + loans.size()))
                .map(loans -> loans.stream()
                        .map(loan -> {
                            RevisionLoanResponseItem item = mapper.map(loan, RevisionLoanResponseItem.class);
                            item.setEmail(loan.getUser().getEmail());
                            item.setName(loan.getUser().getName());
                            item.setBaseSalary(loan.getUser().getBaseSalary());
                            item.setTaxRate(loan.getLoanType().getTaxRate());
                            item.setLoanType(loan.getLoanType().getName());
                            item.setStatus(loan.getStatus().getName());
                            return item;
                        }).toList())
                .map(BuilderResponse::buildOkListLoans);
    }

}
