package com.cs301p.easy_ecomm.daoClasses;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.stereotype.Component;

import com.cs301p.easy_ecomm.entityClasses.Seller;
import com.cs301p.easy_ecomm.mappers.SellerMapper;

// @Component
public class SellerDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager platformTransactionManager;

    public SellerDAO(DataSource dataSource, PlatformTransactionManager platformTransactionManager, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.platformTransactionManager = platformTransactionManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setDataSource(DataSource dataSource){
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
    public int addSeller(Seller seller) {
        int count = 0;
        String sql = "INSERT INTO seller(name, email, password, phone, walletId) VALUES (?, ?, ?, ?, ?);";

        count = this.jdbcTemplate.update(sql, seller.getName(), seller.getEmail(), seller.getPassword(),
                seller.getPhone(), seller.getWalletId());

        return (count);
    }

    public Seller getSellerById(Seller seller) {
        String sql = "SELECT * FROM seller WHERE id=" + seller;
        List <Seller> sellers = this.jdbcTemplate.query(sql, new SellerMapper());

        return sellers.get(0);
    }

    public Seller getSellerByEmail(Seller seller) {
        String sql = "SELECT * FROM seller WHERE email=" + seller.getEmail();
        List<Seller> sellers = this.jdbcTemplate.query(sql, new SellerMapper());

        return sellers.get(0);
    }

    public List<Seller> getAllSellers() {
        String sql = "SELECT * FROM seller;";
        List<Seller> sellers = this.jdbcTemplate.query(sql, new SellerMapper());

        return sellers;
    }

    public int updateSeller(Seller seller) {
        int count = 0;
        String sql = "UPDATE seller SET phone=?, email=? WHERE id=?;";

        count = this.jdbcTemplate.update(sql, seller.getPhone(), seller.getEmail(), seller.getId());

        return (count);
    }

    public int deleteSeller(Seller seller){
        int count = 0;
        String sql = "DELETE FROM seller WHERE id=?;";

        count = this.jdbcTemplate.update(sql, seller.getId());

        return (count);
    }
}
