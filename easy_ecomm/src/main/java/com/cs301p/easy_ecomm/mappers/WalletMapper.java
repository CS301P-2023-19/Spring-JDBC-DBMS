package com.cs301p.easy_ecomm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cs301p.easy_ecomm.entityClasses.Wallet;

public class WalletMapper implements RowMapper <Wallet> {
    @Override
    public Wallet mapRow(ResultSet rs, int arg1) throws SQLException {
        Wallet w = new Wallet();
        w.setId(rs.getInt("id"));
        w.setCredit_card_no(rs.getString("credit_card_no"));
        w.setMoney(rs.getFloat("money"));

        return w;
    }
}
