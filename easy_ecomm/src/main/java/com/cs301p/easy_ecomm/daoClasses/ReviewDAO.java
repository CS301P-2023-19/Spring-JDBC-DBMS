package com.cs301p.easy_ecomm.daoClasses;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.stereotype.Component;

import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Product;
import com.cs301p.easy_ecomm.entityClasses.Review;
import com.cs301p.easy_ecomm.mappers.ReviewDataResponseMapper;
import com.cs301p.easy_ecomm.mappers.ReviewMapper;
import com.cs301p.easy_ecomm.responseClasses.ReviewDataResponse;

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

        if (reviews.size() == 0) {
            return (null);
        }

        return reviews.get(0);
    }

    public Review getReviewByCustomerAndProduct(Customer customer, Product product) {
        String sql = "SELECT r.id, r.productId, c.name as customerName, p.name as productName, r.stars, r.content FROM review r, customer c, product p WHERE customerId=" + customer.getId() + "AND productId=" + product.getId();
        List<Review> reviews = this.jdbcTemplate.query(sql, new ReviewMapper());

        if (reviews.size() == 0) {
            return (null);
        }

        return reviews.get(0);
    }

    public List<ReviewDataResponse> getReviewsByCustomer(Customer customer) {
        String sql = "SELECT r.id, r.productId, c.name as customerName, p.name as productName, r.stars, r.content FROM review r, customer c, product p WHERE r.productId=p.id AND r.customerId=" + customer.getId() + " AND r.customerId=c.id";
        List<ReviewDataResponse> reviews = this.jdbcTemplate.query(sql, new ReviewDataResponseMapper());

        if (reviews.size() == 0) {
            System.out.println("No reviwes found by customer with Id: " + customer.getId());
        }

        return reviews;
    }

    public List<ReviewDataResponse> getAllReviews() {
        String sql = "SELECT r.id, r.productId, c.name as customerName, p.name as productName, r.stars, r.content FROM review r, customer c, product p WHERE r.productId=p.id AND r.customerId=c.id";
        List<ReviewDataResponse> reviews = this.jdbcTemplate.query(sql, new ReviewDataResponseMapper());

        if (reviews.size() == 0) {
            System.out.println("No reviews found.");
        }

        return reviews;
    }

    public List<ReviewDataResponse> getReviewsByProduct(Product product) {
        String sql = "SELECT r.id, r.productId, c.name as customerName, p.name as productName, r.stars, r.content FROM review r, customer c, product p WHERE r.productId=p.id AND r.productId=" + product.getId() + " AND r.customerId=c.id";
        List<ReviewDataResponse> reviews = this.jdbcTemplate.query(sql, new ReviewDataResponseMapper());

        if (reviews.size() == 0) {
            System.out.println("No reviews found for product with Id: " + product.getId());
        }

        return reviews;
    }

    public int addReview(Review review) {
        int count = 0;
        String sql = "INSERT INTO review(customerId, productId, stars, content) VALUES (?, ?, ?, ?);";

        try {
            count = this.jdbcTemplate.update(sql, review.getCustomerId(), review.getProductId(), review.getStars(),
                    review.getContent());
        } catch (Exception e) {
            return (-1);
        }

        return (count);
    }

    public int updateReview(Review review) {
        int count = 0;
        String sql = "UPDATE review SET stars=?, content=? WHERE reviewId=?;";

        try {
            count = this.jdbcTemplate.update(sql, review.getStars(), review.getContent(), review.getId());
        } catch (Exception e) {
            return (-1);
        }

        return (count);
    }

    public int deleteReview(Review review) {
        int count = 0;
        String sql = "DELETE FROM review WHERE id=?;";

        try {
            count = this.jdbcTemplate.update(sql, review.getId());
        } catch (Exception e) {
            return (-1);
        }

        return (count);
    }

}
