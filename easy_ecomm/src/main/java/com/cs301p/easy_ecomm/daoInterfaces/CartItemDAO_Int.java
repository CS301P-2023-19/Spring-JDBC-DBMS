package com.cs301p.easy_ecomm.daoInterfaces;

import java.util.List;

import com.cs301p.easy_ecomm.entityClasses.CartItem;
import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.responseClasses.CartItemDataResponse;

public interface CartItemDAO_Int {
    public List<CartItemDataResponse> listCartItems(Customer customer);

    public int addCartItem(CartItem cartItem);

    public int updateCartItem(CartItem cartItem);

    public int deleteCartItem(CartItem cartItem);
}
