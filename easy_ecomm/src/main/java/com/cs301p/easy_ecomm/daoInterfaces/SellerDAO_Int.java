package com.cs301p.easy_ecomm.daoInterfaces;

import java.util.List;

import com.cs301p.easy_ecomm.entityClasses.Seller;

public interface SellerDAO_Int {
    public int addSeller(Seller seller);

    public int deleteSeller(Seller seller);

    public int updateSeller(Seller seller);

    public Seller getSellerByEmail(Seller seller);

    public Seller getSellerById(Seller seller);

    public List<Seller> getAllSellers();
}
