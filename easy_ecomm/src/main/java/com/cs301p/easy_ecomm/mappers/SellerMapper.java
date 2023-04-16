package com.cs301p.easy_ecomm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cs301p.easy_ecomm.entityClasses.Seller;

public class SellerMapper implements RowMapper <Seller> {
    @Override
    public Seller mapRow(ResultSet rs, int arg1) throws SQLException {
        Seller s = new Seller();
        s.setId(rs.getInt("id"));
        s.setName(rs.getString("name"));
        s.setEmail(rs.getString("email"));
        s.setPassword(rs.getString("password"));
        s.setPhone(rs.getString("phone"));
        s.setWalletId(rs.getInt("walletId"));

        return s;
    }
}

