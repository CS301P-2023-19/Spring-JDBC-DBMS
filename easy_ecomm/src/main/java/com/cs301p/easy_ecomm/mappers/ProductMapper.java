package com.cs301p.easy_ecomm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cs301p.easy_ecomm.entityClasses.Product;

public class ProductMapper implements RowMapper <Product> {
    @Override
    public Product mapRow(ResultSet rs, int arg1) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setType(rs.getString("type"));
        p.setName(rs.getString("name"));
        p.setSellerId(rs.getInt("sellerId"));
        p.setPrice(rs.getFloat("price"));
        p.setQuantityAvailable(rs.getInt("quantityAvailable"));

        return p;
    }
}
