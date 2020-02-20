package com.se.sample.config;

import com.se.sample.service.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@Component
public class DataSourceConfigCloud {

    private final Logger logger = LoggerFactory.getLogger(Counter.class);


    @PostConstruct
    public void init() {
        logger.info("DB_HOST: ", System.getenv("DB_HOST"));
        logger.info("DB_NAME: ", System.getenv("DB_NAME"));
        logger.info("DB_USER: ", System.getenv("DB_USER"));
        logger.info("DB_PASSWORD: ", System.getenv("DB_PASSWORD"));
    }

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    @Primary
    public DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .url("jdbc:mysql://" + System.getenv("DB_HOST")
                        + "/"
                        + System.getenv("DB_NAME"))
                .username(System.getenv("DB_USER"))
                .password(System.getenv("DB_PASSWORD"))
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}
