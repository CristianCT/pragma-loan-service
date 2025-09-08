package co.com.cristiancabarcas.r2dbc;

import co.com.cristiancabarcas.model.commons.LoanStatusEnum;
import co.com.cristiancabarcas.model.commons.LoanTypeEnum;
import co.com.cristiancabarcas.model.loan.Loan;
import co.com.cristiancabarcas.model.loan.gateways.LoanRepository;
import co.com.cristiancabarcas.model.loantype.LoanType;
import co.com.cristiancabarcas.model.status.LoanStatus;
import co.com.cristiancabarcas.model.user.User;
import co.com.cristiancabarcas.r2dbc.entities.LoanEntity;
import co.com.cristiancabarcas.r2dbc.entities.RevisionLoansEntity;
import co.com.cristiancabarcas.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
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
        loanEntity.setStatusId(LoanStatusEnum.PENDING.getCode());

        log.info("Saving loan");
        return repository.save(loanEntity)
                .map(loanSaved -> {
                    Loan result = mapper.map(loanSaved, Loan.class);
                    LoanStatusEnum loanStatusEnum = LoanStatusEnum.fromCode(loanSaved.getStatusId());
                    LoanTypeEnum loanType1 = LoanTypeEnum.fromCode(loanSaved.getLoanTypeId());

                    result.setLoanType(LoanType.builder().name(loanType1.getName()).description(loanType1.getDescription()).build());
                    result.setStatus(LoanStatus.builder().name(loanStatusEnum.getName()).description(loanStatusEnum.getDescription()).build());
                    return result;
                })
                .onErrorResume(throwable ->
                        DataIntegrityViolationException.class.equals(throwable.getClass())
                                ? Mono.empty()
                                : Mono.error(throwable));
    }

    @Override
    @Transactional
    public Mono<List<Loan>> findByParams(Map<String, String> params) {

        log.info(String.format("Finding loans by params: %s", params));

        return repository.findLoansForRevision()
                .map(this::buildLoan)
                .collectList()
                .switchIfEmpty(Mono.empty());
    }

    private Loan buildLoan(RevisionLoansEntity revisionLoansEntity) {

        return Loan.builder()
                .amount(revisionLoansEntity.getAmount())
                .termInMonths(revisionLoansEntity.getTermInMonths())
                .status(LoanStatus.builder().name(revisionLoansEntity.getStatus()).build())
                .loanType(LoanType.builder()
                        .name(revisionLoansEntity.getLoanType())
                        .taxRate(revisionLoansEntity.getTaxRate())
                        .build())
                .user(User.builder()
                        .email(revisionLoansEntity.getEmail())
                        .name(revisionLoansEntity.getName())
                        .baseSalary(revisionLoansEntity.getBaseSalary())
                        .build())
                .build();
    }
}
