package co.com.cristiancabarcas.r2dbc;

import co.com.cristiancabarcas.model.commons.LoanType;
import co.com.cristiancabarcas.model.commons.Status;
import co.com.cristiancabarcas.model.loan.Loan;
import co.com.cristiancabarcas.model.loan.gateways.LoanRepository;
import co.com.cristiancabarcas.r2dbc.entities.LoanEntity;
import co.com.cristiancabarcas.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Repository
public class MyReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<Loan, LoanEntity, String, MyReactiveRepository>
        implements LoanRepository {

    private static final Logger log = Logger.getLogger(MyReactiveRepositoryAdapter.class.getName());

    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Loan.class));
    }

    @Override
    @Transactional
    public Mono<Loan> save(Loan loan, String userId, Integer loanType) {

        LoanEntity loanEntity = mapper.map(loan, LoanEntity.class);
        loanEntity.setUserId(userId);
        loanEntity.setLoanTypeId(loanType);
        loanEntity.setStatusId(Status.PENDING_REVIEW.getCode());

        log.info("Saving loan");
        return repository.save(loanEntity)
                .map(loanSaved -> {
                    Loan result = mapper.map(loanSaved, Loan.class);
                    result.setLoanType(LoanType.fromCode(loanSaved.getLoanTypeId()));
                    result.setStatus(Status.fromCode(loanSaved.getStatusId()));
                    return result;
                })
                .onErrorResume(throwable ->
                        DataIntegrityViolationException.class.equals(throwable.getClass())
                                ? Mono.empty()
                                : Mono.error(throwable));
    }

}
