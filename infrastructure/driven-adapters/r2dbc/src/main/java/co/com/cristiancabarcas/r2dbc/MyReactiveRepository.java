package co.com.cristiancabarcas.r2dbc;

import co.com.cristiancabarcas.r2dbc.entities.LoanEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MyReactiveRepository extends ReactiveCrudRepository<LoanEntity, String>, ReactiveQueryByExampleExecutor<LoanEntity> {

}
