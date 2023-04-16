package com.cs301p.easy_ecomm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cs301p.easy_ecomm.entityClasses.Customer;

public class CustomerMapper implements RowMapper <Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int arg1) throws SQLException {
        Customer c = new Customer();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setEmail(rs.getString("email"));
        c.setPassword(rs.getString("password"));
        c.setPhone(rs.getString("phone"));
        c.setAddress(rs.getString("address"));
        c.setWalletId(rs.getInt("walletId"));

        return c;
    }
}
