package com.cs301p.easy_ecomm.daoInterfaces;

import java.util.List;

import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Transaction;

public interface TransactionDAO_Int {
    public List<Transaction> getTransactionsByCustomer(Customer customer);

    public int updateTransaction(Transaction transaction);
}
