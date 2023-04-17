package com.cs301p.easy_ecomm.daoClasses;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.cs301p.easy_ecomm.entityClasses.CartItem;
import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Product;
import com.cs301p.easy_ecomm.mappers.CartItemDataResponseMapper;
import com.cs301p.easy_ecomm.responseClasses.CartItemDataResponse;

// @Component
public class CartItemDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager platformTransactionManager;

    public CartItemDAO(DataSource dataSource, PlatformTransactionManager platformTransactionManager,
            JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.platformTransactionManager = platformTransactionManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return this.platformTransactionManager;
    }

    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    // Now, write query functions.
    public List<CartItemDataResponse> listCartItems(Customer customer) {
        String sql = "SELECT c.productId, p.name, p.price, c.quantity FROM cart_item c, product p WHERE c.customerId="
                + customer.getId() + "AND c.productId = p.id";
        List<CartItemDataResponse> cartItems = this.jdbcTemplate.query(sql, new CartItemDataResponseMapper());

        return cartItems;
    }

    public int addCartItem(CartItem cartItem) {
        int count = 0;
        String sql = "INSERT INTO cart_item (customerId, productId, quantity) VALUES (?, ?, ?)";

        count = this.jdbcTemplate.update(sql, cartItem.getCustomerId(), cartItem.getProductId(),
                cartItem.getQuantity());

        return (count);
    }

    public int updateCartItem(CartItem cartItem) {
        int count = 0;
        String sql = "UPDATE cart_item SET quantity=? WHERE productId=? AND customerId=?;";

        count = this.jdbcTemplate.update(sql, cartItem.getProductId(), cartItem.getCustomerId());

        return (count);
    }

    public int deleteCartItem(CartItem cartItem) {
        int count = 0;
        String sql = "DELETE FROM cart_item WHERE customerId=? AND productId=?;";

        count = this.jdbcTemplate.update(sql, cartItem.getCustomerId(), cartItem.getProductId());

        return (count);
    }
}
