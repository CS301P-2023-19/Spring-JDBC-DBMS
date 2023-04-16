package com.cs301p.easy_ecomm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cs301p.easy_ecomm.entityClasses.CartItem;

public class CartItemMapper implements RowMapper <CartItem> {
    @Override
    public CartItem mapRow(ResultSet rs, int arg1) throws SQLException {
        CartItem ci = new CartItem();
        ci.setCustomerId(rs.getInt("customerId"));
        ci.setProductId(rs.getInt("productId"));
        ci.setQuantity(rs.getInt("quantity"));

        return ci;
    }
}
