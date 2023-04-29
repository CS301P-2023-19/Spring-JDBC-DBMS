package com.cs301p.easy_ecomm.daoClasses;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.cs301p.easy_ecomm.daoInterfaces.WalletDAO_Int;
import com.cs301p.easy_ecomm.entityClasses.Wallet;
import com.cs301p.easy_ecomm.mappers.WalletMapper;

public class WalletDAO implements WalletDAO_Int {
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

        if(wallets.size() == 0){
            return(null);
        }

        return wallets.get(0);
    }

    public int addWallet(Wallet wallet) {
        int count = 0;
        String sql = "INSERT INTO wallet(credit_card_no, money) VALUES (?, ?);";

        try {
            count = this.jdbcTemplate.update(sql, wallet.getCredit_card_no(), wallet.getMoney());
        } catch (DuplicateKeyException e) {
            System.out.println("Credit card already in use! Can not link new wallet");
            return(-1);
        }

        return (count);
    }

    public int updateWallet(Wallet wallet) {
        int count = 0;
        String sql = "UPDATE wallet SET money=?, credit_card_no=? WHERE id=?;";

        try{
            count = this.jdbcTemplate.update(sql, wallet.getMoney(), wallet.getCredit_card_no(), wallet.getId());
        }catch (Exception ex){
            return(-1);
        }

        return (count);
    }

    public int deleteWallet(Wallet wallet){
        int count = 0;
        String sql = "DELETE FROM wallet WHERE id=?;";

        try {            
            count = this.jdbcTemplate.update(sql, wallet.getId());
        } catch (Exception e) {
            return(-1);
        }

        return (count);
    }
}
