package com.cs301p.easy_ecomm.daoClasses;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.stereotype.Component;

import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.mappers.CustomerMapper;

// @Component
public class CustomerDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager platformTransactionManager;

    public CustomerDAO(DataSource dataSource, PlatformTransactionManager platformTransactionManager,
            JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.platformTransactionManager = platformTransactionManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return this.platformTransactionManager;
    }

    public void getPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    // Now, write query functions.
    public int addCustomer(Customer customer) {
        int count = 0;
        String sql = "INSERT INTO customer(name, email, password, phone, address) VALUES (?, ?, ?, ?, ?);";

        count = this.jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getPassword(),
                customer.getPhone(), customer.getAddress());

        return (count);
    }

    public Customer getCustomerById(Customer customer) {
        String sql = "SELECT * FROM customer WHERE id=" + customer.getId();
        List<Customer> customers = this.jdbcTemplate.query(sql, new CustomerMapper());

        return customers.get(0);
    }

    public Customer getCustomerByEmail(Customer customer) {
        String sql = "SELECT * FROM customer WHERE email=" + customer.getEmail();
        List<Customer> customers = this.jdbcTemplate.query(sql, new CustomerMapper());

        return customers.get(0);
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer;";
        List<Customer> customers = this.jdbcTemplate.query(sql, new CustomerMapper());

        return customers;
    }

    public int updateCustomer(Customer customer) {
        int count = 0;
        String sql = "UPDATE customer SET phone=?, address=?, email=?, walletId=? WHERE id=?;";

        count = this.jdbcTemplate.update(sql, customer.getPhone(), customer.getAddress(), customer.getEmail(), customer.getWalletId(), customer.getId());

        return (count);
    }

    public int deleteCustomer(Customer customer){
        int count = 0;
        String sql = "DELETE FROM customer WHERE id=?;";

        count = this.jdbcTemplate.update(sql, customer.getId());

        return (count);
    }
}
