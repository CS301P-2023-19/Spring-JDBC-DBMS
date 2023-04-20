package com.cs301p.easy_ecomm.daoClasses;

import java.util.List;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.stereotype.Component;

import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Transaction;
import com.cs301p.easy_ecomm.mappers.TransactionMapper;

// @Component
public class TransactionDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager platformTransactionManager;

    public TransactionDAO(DataSource dataSource, PlatformTransactionManager platformTransactionManager,
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
    public List<Transaction> getTransactionsByCustomer(Customer customer) {
        String sql = "SELECT * FROM transaction WHERE customerId=" + customer.getId();
        List<Transaction> transactions = this.jdbcTemplate.query(sql, new TransactionMapper());

        if(transactions.size() == 0){
            System.out.println("No transactions found!");
            return(null);
        }

        return transactions;
    }

    public int updateTransaction(Transaction transaction) {
        int count = 0;
        
        String sql = "SELECT * FROM transaction WHERE customerId=" + transaction.getCustomerId() + " AND productId="
                + transaction.getProductId() + " AND id=" + transaction.getId();
        List<Transaction> transactions = this.jdbcTemplate.query(sql, new TransactionMapper());

        if(transactions.size() == 0){
            System.out.println("No matching transactions found!");
            return(-1);
        }

        // Check 7-day return policy.
        LocalDate dateDB = transactions.get(0).getDate().toLocalDate();
        LocalDate dateSS = LocalDate.now();
        long days = ChronoUnit.DAYS.between(dateDB, dateSS);

        if (days <= 7) {
            sql = "UPDATE transaction SET returnStatus=? WHERE customerId=? AND productId=? AND id=?;";
            count = this.jdbcTemplate.update(sql, transaction.getReturnStatus(), transaction.getCustomerId(),
                    transaction.getProductId(), transaction.getId());
        }
        else{
            System.out.println("Too late to return the product.");
            return(-1);
        }

        return (count);
    }
}
