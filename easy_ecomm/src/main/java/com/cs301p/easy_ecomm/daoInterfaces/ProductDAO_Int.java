package com.cs301p.easy_ecomm.daoInterfaces;

import java.util.List;

import com.cs301p.easy_ecomm.entityClasses.Product;
import com.cs301p.easy_ecomm.entityClasses.Seller;
import com.cs301p.easy_ecomm.utilClasses.FilterBy;
import com.cs301p.easy_ecomm.utilClasses.OrderBy;

public interface ProductDAO_Int {
    public Product getProductById(Product product);

    public Seller getSellerByProductId(Product product);

    public int addProduct(Product product);

    public int deleteProduct(Product product);

    public int updateProduct(Product product);

    public List<Product> listProducts(FilterBy filter_by, OrderBy order_by);
}
