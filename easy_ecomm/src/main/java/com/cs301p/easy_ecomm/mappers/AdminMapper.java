package com.cs301p.easy_ecomm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cs301p.easy_ecomm.entityClasses.Admin;

public class AdminMapper implements RowMapper <Admin> {
    @Override
    public Admin mapRow(ResultSet rs, int arg1) throws SQLException {
        Admin a = new Admin();
        a.setId(rs.getInt("id"));
        a.setA_name(rs.getString("a_name"));
        a.setA_password(rs.getString("a_password"));

        return a;
    }
}
