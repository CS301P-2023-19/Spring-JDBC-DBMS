package com.cs301p.easy_ecomm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cs301p.easy_ecomm.responseClasses.CartItemDataResponse;

public class CartItemDataResponseMapper implements RowMapper <CartItemDataResponse> {
    @Override
    public CartItemDataResponse mapRow(ResultSet rs, int arg1) throws SQLException {
        CartItemDataResponse ci = new CartItemDataResponse();
        ci.setName(rs.getString("name"));
        ci.setPrice(rs.getFloat("price"));
        ci.setQuantity(rs.getInt("quantity"));
        ci.setProductId(rs.getInt("productId"));

        return ci;
    }
}
