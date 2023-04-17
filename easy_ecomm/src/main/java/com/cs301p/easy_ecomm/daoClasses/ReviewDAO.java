package com.cs301p.easy_ecomm.daoClasses;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.stereotype.Component;

import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Product;
import com.cs301p.easy_ecomm.entityClasses.Review;
import com.cs301p.easy_ecomm.mappers.ReviewMapper;

// @Component
public class ReviewDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager platformTransactionManager;

    public ReviewDAO(DataSource dataSource, PlatformTransactionManager platformTransactionManager,
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

    public void getPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    // Now, write query functions.
    public Review getReviewById(Review review) {
        String sql = "SELECT * FROM review WHERE id=" + review.getId();
        List<Review> reviews = this.jdbcTemplate.query(sql, new ReviewMapper());

        return reviews.get(0);
    }

    public Review getReviewByCustomerAndProduct(Customer customer, Product product) {
        String sql = "SELECT * FROM review WHERE customerId=" + customer.getId() + "AND productId=" + product.getId();
        List<Review> reviews = this.jdbcTemplate.query(sql, new ReviewMapper());

        return reviews.get(0);
    }

    public List<Review> getReviewsByCustomer(Customer customer) {
        String sql = "SELECT * FROM review WHERE customerId=" + customer.getId();
        List<Review> reviews = this.jdbcTemplate.query(sql, new ReviewMapper());

        return reviews;
    }

    public List<Review> getReviewsByProduct(Product product) {
        String sql = "SELECT * FROM review WHERE productId=" + product.getId();
        List<Review> reviews = this.jdbcTemplate.query(sql, new ReviewMapper());

        return reviews;
    }

    public int addReview(Review review) {
        int count = 0;
        String sql = "INSERT INTO review(customerId, productId, stars, content) VALUES (?, ?, ?, ?);";

        count = this.jdbcTemplate.update(sql, review.getCustomerId(), review.getProductId(), review.getStars(), review.getContent());

        return (count);
    }

    public int updateReview(Review review) {
        int count = 0;
        String sql = "UPDATE review SET stars=?, content=? WHERE reviewId=?;";

        count = this.jdbcTemplate.update(sql, review.getStars(), review.getContent(), review.getId());

        return (count);
    }

    public int deleteReview(Review review){
        int count = 0;
        String sql = "DELETE FROM review WHERE id=?;";

        count = this.jdbcTemplate.update(sql, review.getId());

        return (count);
    }

}
