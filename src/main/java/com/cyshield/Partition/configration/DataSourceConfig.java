package com.cyshield.Partition.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // Set your specific database URL, username, and password here
        dataSource.setDriverClassName("org.h2.Driver"); // Change to your DB driver
        dataSource.setUrl("jdbc:h2:mem:testdb"); // Change to your DB URL
        dataSource.setUsername("sa"); // Change to your DB username
        dataSource.setPassword(""); // Change to your DB password
        return dataSource;
    }
}
