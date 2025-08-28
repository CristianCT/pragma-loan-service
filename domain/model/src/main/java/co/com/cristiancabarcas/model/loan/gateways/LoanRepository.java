package co.com.cristiancabarcas.model.loan.gateways;

import co.com.cristiancabarcas.model.loan.Loan;
import reactor.core.publisher.Mono;

public interface LoanRepository {

    Mono<Loan> save(Loan loan, String userId, Integer loanType);
}
