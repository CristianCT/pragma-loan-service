package co.com.cristiancabarcas.r2dbc;

import co.com.cristiancabarcas.r2dbc.entities.LoanEntity;
import co.com.cristiancabarcas.r2dbc.entities.RevisionLoansEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MyReactiveRepository extends ReactiveCrudRepository<LoanEntity, String>, ReactiveQueryByExampleExecutor<LoanEntity> {

    @Query("""
        SELECT 
            L.amount AS amount,
            L.term_in_months AS term_in_months,
            U.email AS email,
            U.name AS name,
            LT.name AS loan_type,
            LT.tax_rate AS tax_rate,
            S.name AS status,
            U.salary AS base_salary
        FROM loans L
        INNER JOIN loan_type LT 
            ON LT.id = L.loan_type_id
        INNER JOIN status S
            ON S.id = L.status_id
        INNER JOIN users U
            ON U.id = L.user_id
        WHERE L.status_id IN (1, 3, 4)
    """)
    Flux<RevisionLoansEntity> findLoansForRevision();

}
