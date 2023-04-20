package com.cs301p.easy_ecomm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cs301p.easy_ecomm.responseClasses.ShippingDetailsDataResponse;

public class ShippingDetailsDataResponseMapper implements RowMapper <ShippingDetailsDataResponse> {
    @Override
    public ShippingDetailsDataResponse mapRow(ResultSet rs, int arg1) throws SQLException {
        ShippingDetailsDataResponse sd = new ShippingDetailsDataResponse();
        sd.setTransactionId(rs.getInt("id"));        
        sd.setProductId(rs.getInt("productId"));        
        sd.setCustomerName(rs.getString("name"));        
        sd.setCustomerAddress(rs.getString("address"));  
        sd.setPhone(rs.getString("phone"));      

        return sd;
    }
}
