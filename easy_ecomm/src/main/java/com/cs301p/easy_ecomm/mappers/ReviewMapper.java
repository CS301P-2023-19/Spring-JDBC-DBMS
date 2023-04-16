package com.cs301p.easy_ecomm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cs301p.easy_ecomm.entityClasses.Review;

public class ReviewMapper implements RowMapper <Review> {
    @Override
    public Review mapRow(ResultSet rs, int arg1) throws SQLException {
        Review r = new Review();
        r.setId(rs.getInt("id"));
        r.setCustomerId(rs.getInt("customerId"));
        r.setProductId(rs.getInt("productId"));
        r.setStars(rs.getInt("stars"));
        r.setContent(rs.getString("content"));

        return r;
    }
    
}
