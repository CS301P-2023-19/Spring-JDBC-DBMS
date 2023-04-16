package com.cs301p.easy_ecomm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cs301p.easy_ecomm.daoClasses.CustomerDAO;
import com.cs301p.easy_ecomm.daoClasses.WalletDAO;
import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Wallet;
import com.cs301p.easy_ecomm.factoryClasses.DAO_Factory;

@SpringBootApplication
public class EasyEcommApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(EasyEcommApplication.class, args);
        DAO_Factory dao_Factory = (DAO_Factory)applicationContext.getBean(DAO_Factory.class);

        // CustomerDAO customerDAO = dao_Factory.getCustomerDAO();
        // WalletDAO walletDAO = dao_Factory.getWalletDAO();
        // Wallet w1 = new Wallet(0, "1211-3344-2556-1095", (float)440021.14);      // Auto-increment, can pass any id while inserting.
        // Customer c1 = new Customer(0, "Customer 1", "em1@tm.com", "pass123", "1234432100", "Chennai, Tamil Nadu", 3);

        // int count = walletDAO.addWallet(w1);
        // System.out.println(count + " records added to wallet table.");
        // count = customerDAO.addCustomer(c1);
        // System.out.println(count + " records added to customer table.");
    }
}
