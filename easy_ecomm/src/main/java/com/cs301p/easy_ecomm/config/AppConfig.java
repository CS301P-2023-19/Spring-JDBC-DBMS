package com.cs301p.easy_ecomm.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.cs301p.easy_ecomm.daoClasses.AdminDAO;
import com.cs301p.easy_ecomm.daoClasses.CartItemDAO;
import com.cs301p.easy_ecomm.daoClasses.CustomerDAO;
import com.cs301p.easy_ecomm.daoClasses.ProductDAO;
import com.cs301p.easy_ecomm.daoClasses.ReviewDAO;
import com.cs301p.easy_ecomm.daoClasses.SellerDAO;
import com.cs301p.easy_ecomm.daoClasses.TransactionDAO;
import com.cs301p.easy_ecomm.daoClasses.WalletDAO;

@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class AppConfig {
    
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplator() {
        return new JdbcTemplate(dataSource());
    }
    
    @Bean
    public CustomerDAO customerDAO() {
        return new CustomerDAO(dataSource(), transactionManager(), jdbcTemplator()); // Set jdbc template.
    }
    
    @Bean
    public SellerDAO sellerDAO() {
        return new SellerDAO(dataSource(), transactionManager(), jdbcTemplator());
    }
    
    @Bean
    public ProductDAO productDAO() {
        return new ProductDAO(dataSource(), transactionManager(), jdbcTemplator());
    }
    
    @Bean
    public AdminDAO adminDAO() {
        return new AdminDAO(dataSource(), transactionManager(), jdbcTemplator());
    }
    
    @Bean
    public CartItemDAO cartDAO() {
        return new CartItemDAO(dataSource(), transactionManager(), jdbcTemplator());
    }
    
    @Bean
    public ReviewDAO reviewDAO() {
        return new ReviewDAO(dataSource(), transactionManager(), jdbcTemplator());
    }
    
    @Bean
    public TransactionDAO transactionDAO() {
        return new TransactionDAO(dataSource(), transactionManager(), jdbcTemplator());
    }
    
    @Bean
    public WalletDAO walletDAO() {
        return new WalletDAO(dataSource(), transactionManager(), jdbcTemplator());
    }
}
