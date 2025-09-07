package co.com.cristiancabarcas.r2dbc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Autowired
    private MySQLConnectionProperties properties;

    @Bean("flywayDataSource")
    public DataSource flywayDataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        String jdbcUrl = String.format("jdbc:mysql://%s:%d/%s",
                properties.getHost(),
                properties.getPort(),
                properties.getDatabase());

        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource.addDataSourceProperty("useSSL", "false");
        dataSource.addDataSourceProperty("serverTimezone", "America/Bogota");
        dataSource.addDataSourceProperty("allowPublicKeyRetrieval", "true");

        dataSource.setMaximumPoolSize(5);
        dataSource.setMinimumIdle(1);
        dataSource.setConnectionTimeout(30000);
        dataSource.setIdleTimeout(600000);

        return dataSource;
    }

    @Bean
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(flywayDataSource())
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .validateOnMigrate(true)
                .table("flyway_loan_schema_history")
                .load();
    }

    @Bean
    @DependsOn("flyway")
    public FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, null);
    }
}