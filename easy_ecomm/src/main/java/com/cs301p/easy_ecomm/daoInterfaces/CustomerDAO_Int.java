package com.cs301p.easy_ecomm.daoInterfaces;

import java.util.List;

import com.cs301p.easy_ecomm.entityClasses.Customer;

public interface CustomerDAO_Int {
    public Customer getCustomerById(Customer customer);

    public Customer getCustomerByEmail(Customer customer);

    public List<Customer> getAllCustomers();

    public int addCustomer(Customer customer);

    public int deleteCustomer(Customer customer);

    public int updateCustomer(Customer customer);
}
