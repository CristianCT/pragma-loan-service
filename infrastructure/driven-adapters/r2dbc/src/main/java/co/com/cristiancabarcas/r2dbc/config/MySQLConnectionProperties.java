package co.com.cristiancabarcas.r2dbc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "adapters.r2dbc")
public class MySQLConnectionProperties {
    private String host;
    private Integer port;
    private String database;
    private String username;
    private String password;
}
