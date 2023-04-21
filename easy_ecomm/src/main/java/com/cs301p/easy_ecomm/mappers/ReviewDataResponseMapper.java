package com.cs301p.easy_ecomm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cs301p.easy_ecomm.responseClasses.ReviewDataResponse;

public class ReviewDataResponseMapper implements RowMapper <ReviewDataResponse> {
    @Override
    public ReviewDataResponse mapRow(ResultSet rs, int arg1) throws SQLException {
        ReviewDataResponse r = new ReviewDataResponse();
        r.setId(rs.getInt("id"));
        r.setCustomerName(rs.getString("customerName"));
        r.setProductId(rs.getInt("productId"));
        r.setProductName(rs.getString("productName"));
        r.setStars(rs.getInt("stars"));
        r.setContent(rs.getString("content"));

        return r;
    }
}

