package com.cs301p.easy_ecomm.daoClasses;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.stereotype.Component;

import com.cs301p.easy_ecomm.entityClasses.Wallet;
import com.cs301p.easy_ecomm.mappers.WalletMapper;

// @Component
public class WalletDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager platformTransactionManager;

    public WalletDAO(DataSource dataSource, PlatformTransactionManager platformTransactionManager, JdbcTemplate jdbcTemplate) {
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
    public Wallet getWalletById(Wallet wallet) {
        String sql = "SELECT * FROM wallet WHERE id=" + wallet.getId();
        List <Wallet> wallets = this.jdbcTemplate.query(sql, new WalletMapper());

        return wallets.get(0);
    }

    public int addWallet(Wallet wallet) {
        int count = 0;
        String sql = "INSERT INTO wallet(credit_card_no, money) VALUES (?, ?);";

        count = this.jdbcTemplate.update(sql, wallet.getCredit_card_no(), wallet.getMoney());

        return (count);
    }

    public int updateWallet(Wallet wallet) {
        int count = 0;
        String sql = "UPDATE wallet SET money=?, credit_card_no=? WHERE id=?;";

        count = this.jdbcTemplate.update(sql, wallet.getMoney(), wallet.getCredit_card_no(), wallet.getId());

        return (count);
    }

    public int deleteWallet(Wallet wallet){
        int count = 0;
        String sql = "DELETE FROM wallet WHERE id=?;";

        count = this.jdbcTemplate.update(sql, wallet.getId());

        return (count);
    }
}
