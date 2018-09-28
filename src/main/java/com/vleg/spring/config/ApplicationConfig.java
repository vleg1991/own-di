package com.vleg.spring.config;

import com.vleg.spring.annotation.Bean;
import com.vleg.spring.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class ApplicationConfig {

    @Bean(name = "h2-db-connection")
    public Connection getDbConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "vleg");
        properties.setProperty("password", "");
        return DriverManager.getConnection("jdbc:h2:~/embedded/testdb", properties);
    }
}
