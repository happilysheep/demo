package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author wangzhijun
 * @create 2018-09-21 17:43
 * @description
 */

@Configuration
public class DataSourceConfig {
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.pgsql.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.pgsql.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.pgsql.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.pgsql.password"));
        return dataSource;
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate pgJdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }


    public DataSource myDataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.mysql.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.mysql.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.mysql.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.mysql.password"));
        dataSource.setInitialSize(2);
        dataSource.setMaxActive(20);
        dataSource.setMinIdle(0);
        dataSource.setMaxWait(60000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setPoolPreparedStatements(false);
        return dataSource;
    }

    @Bean
    public JdbcTemplate myJdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(myDataSource());
        return jdbcTemplate;
    }
}
