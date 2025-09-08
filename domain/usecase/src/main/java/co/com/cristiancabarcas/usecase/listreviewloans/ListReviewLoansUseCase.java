package co.com.cristiancabarcas.usecase.listreviewloans;

import co.com.cristiancabarcas.model.commons.DomainError;
import co.com.cristiancabarcas.model.commons.errors.LoansEmptyException;
import co.com.cristiancabarcas.model.loan.Loan;
import co.com.cristiancabarcas.model.loan.gateways.LoanRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ListReviewLoansUseCase {

    private final LoanRepository loanRepository;
    private static final Logger log = Logger.getLogger(ListReviewLoansUseCase.class.getName());

    public Mono<List<Loan>> execute(Map<String, String> params) {
        log.info("::::: INIT LIST REVIEW LOANS :::::");
        return Mono.just(params)
                .map(this::validateParams)
                .flatMap(loanRepository::findByParams)
                .filter(loans -> !loans.isEmpty())
                .map(loans -> loans.stream().map(loan -> loan.toBuilder()
                        .amountPerMonth(calculateAmountPerMonth(loan)).build()).toList())
                .switchIfEmpty(Mono.error(new LoansEmptyException(DomainError.LOANS_EMPTY.getMessage())))
                .onErrorResume(Mono::error);
    }

    private Double calculateAmountPerMonth(Loan loan) {
        return loan.getAmount() * ((loan.getLoanType().getTaxRate() / 1200) /
                (1 - (Math.pow((1 + (loan.getLoanType().getTaxRate() / 1200)), -loan.getTermInMonths()))));
    }

    private Map<String, String> validateParams(Map<String, String> myParams) {
        return myParams;
    }
}
