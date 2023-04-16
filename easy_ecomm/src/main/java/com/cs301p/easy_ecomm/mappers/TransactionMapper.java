package com.cs301p.easy_ecomm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cs301p.easy_ecomm.entityClasses.Transaction;

public class TransactionMapper implements RowMapper <Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int arg1) throws SQLException {
        Transaction t = new Transaction();
        t.setId(rs.getInt("id"));
        t.setCustomerId(rs.getInt("customerId"));
        t.setSellerId(rs.getInt("sellerId"));
        t.setProductId(rs.getInt("productId"));
        t.setDate(rs.getDate("date"));
        t.setReturnStatus(rs.getBoolean("returnStatus"));

        return t;
    }
}
