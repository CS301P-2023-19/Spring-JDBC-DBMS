package com.cs301p.easy_ecomm.daoInterfaces;

import java.util.List;

import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Product;
import com.cs301p.easy_ecomm.entityClasses.Review;
import com.cs301p.easy_ecomm.responseClasses.ReviewDataResponse;

public interface ReviewDAO_Int {
    public Review getReviewById(Review review);

    public Review getReviewByCustomerAndProduct(Customer customer, Product product);

    public List<ReviewDataResponse> getReviewsByCustomer(Customer customer);

    public List<ReviewDataResponse> getReviewsByProduct(Product product);

    public List<ReviewDataResponse> getAllReviews();

    public int addReview(Review review);

    public int deleteReview(Review review);

    public int updateReview(Review review);
}
