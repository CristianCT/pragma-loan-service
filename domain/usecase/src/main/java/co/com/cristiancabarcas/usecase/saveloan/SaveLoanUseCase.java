package co.com.cristiancabarcas.usecase.saveloan;

import co.com.cristiancabarcas.model.commons.DomainError;
import co.com.cristiancabarcas.model.commons.errors.InvalidAmountException;
import co.com.cristiancabarcas.model.commons.errors.InvalidLoanTypeException;
import co.com.cristiancabarcas.model.commons.errors.InvalidTermInMonthsException;
import co.com.cristiancabarcas.model.commons.errors.UserNotExistException;
import co.com.cristiancabarcas.model.loan.Loan;
import co.com.cristiancabarcas.model.loan.gateways.LoanRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class SaveLoanUseCase {

    private final LoanRepository loanRepository;
    private static final Logger log = Logger.getLogger(SaveLoanUseCase.class.getName());

    public Mono<Loan> execute(Loan loan, String userId, Integer loanType) {
        return Mono.just(loan)
                .map(newLoan -> validLoan(newLoan, loanType))
                .flatMap(loanValidated -> loanRepository.save(loanValidated, userId, loanType))
                .switchIfEmpty(Mono.error(new UserNotExistException("The user does not exist: " + userId)))
                .onErrorResume(Mono::error);
    }

    private Loan validLoan(Loan loan, Integer loanType) {
        if (isNull(loanType)) {
            log.warning(DomainError.INVALID_LOAN_TYPE.getMessage());
            throw new InvalidLoanTypeException(DomainError.INVALID_LOAN_TYPE.getMessage());
        }

        if (isNull(loan.getAmount()) || loan.getAmount() <= 0) {
            log.warning(DomainError.INVALID_AMOUNT.getMessage());
            throw new InvalidAmountException(DomainError.INVALID_AMOUNT.getMessage());
        }

        if (isNull(loan.getTermInMonths()) || loan.getTermInMonths() <= 0) {
            log.warning(DomainError.INVALID_TERM_IN_MONTHS.getMessage());
            throw new InvalidTermInMonthsException(DomainError.INVALID_TERM_IN_MONTHS.getMessage());
        }

        log.info("Loan is valid");
        return loan;
    }
}
