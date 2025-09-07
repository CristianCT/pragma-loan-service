package co.com.cristiancabarcas.model.loan.gateways;

import co.com.cristiancabarcas.model.loan.Loan;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface LoanRepository {

    Mono<Loan> save(Loan loan, String userId, Integer loanType);
    Mono<List<Loan>> findByParams(Map<String, String> params);
}
