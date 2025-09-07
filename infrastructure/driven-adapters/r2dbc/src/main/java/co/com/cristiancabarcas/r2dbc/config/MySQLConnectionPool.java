package co.com.cristiancabarcas.r2dbc.config;

import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;
import java.time.ZoneId;

@Configuration
@EnableTransactionManagement
public class MySQLConnectionPool {

    public static final int INITIAL_SIZE = 12;
    public static final int MAX_SIZE = 15;
    public static final int MAX_IDLE_TIME = 30;

	@Bean("connectionPool")
	public ConnectionPool getConnectionConfig(MySQLConnectionProperties properties) {
		MySqlConnectionConfiguration dbConfiguration = MySqlConnectionConfiguration.builder()
                .host(properties.getHost())
                .port(properties.getPort())
                .database(properties.getDatabase())
                .username(properties.getUsername())
                .password(properties.getPassword())
				.serverZoneId(ZoneId.of("America/Bogota"))
                .build();

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder()
                .connectionFactory(MySqlConnectionFactory.from(dbConfiguration))
                .name("api-mysql-connection-pool")
                .initialSize(INITIAL_SIZE)
                .maxSize(MAX_SIZE)
                .maxIdleTime(Duration.ofMinutes(MAX_IDLE_TIME))
                .validationQuery("SELECT 1")
                .build();

		return new ConnectionPool(poolConfiguration);
	}

	@Bean("connectionFactoryTransactionManager")
	@Primary
	public TransactionManager connectionFactoryTransactionManager(@Qualifier("connectionPool") ConnectionPool connectionPool) {
		return new R2dbcTransactionManager(connectionPool);
	}

	@Bean
	@Primary
	public R2dbcEntityTemplate r2dbcEntityTemplate(@Qualifier("connectionPool") ConnectionPool connectionPool) {
		return new R2dbcEntityTemplate(connectionPool);
	}
}