package co.com.cristiancabarcas.usecase.listreviewloans;

import co.com.cristiancabarcas.model.loan.Loan;
import co.com.cristiancabarcas.model.loan.gateways.LoanRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ListReviewLoansUseCase {

    private final LoanRepository loanRepository;
    private static final Logger log = Logger.getLogger(ListReviewLoansUseCase.class.getName());

//    public Mono<List<Loan>> execute() {
//
//    }

}
