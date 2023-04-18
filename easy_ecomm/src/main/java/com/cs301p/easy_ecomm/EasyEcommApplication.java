package com.cs301p.easy_ecomm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Wallet;
import com.cs301p.easy_ecomm.factoryClasses.DAO_Factory;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.cs301p.easy_ecomm.daoClasses.CartItemDAO;
import com.cs301p.easy_ecomm.daoClasses.CustomerDAO;
import com.cs301p.easy_ecomm.daoClasses.ProductDAO;
import com.cs301p.easy_ecomm.daoClasses.ReviewDAO;
import com.cs301p.easy_ecomm.daoClasses.TransactionDAO;
import com.cs301p.easy_ecomm.daoClasses.WalletDAO;
import com.cs301p.easy_ecomm.entityClasses.CartItem;
import com.cs301p.easy_ecomm.entityClasses.Product;
import com.cs301p.easy_ecomm.entityClasses.Review;
import com.cs301p.easy_ecomm.entityClasses.Transaction;
import com.cs301p.easy_ecomm.mappers.ShippingDetailsDataResponseMapper;
import com.cs301p.easy_ecomm.mappers.TransactionMapper;
import com.cs301p.easy_ecomm.mappers.WalletMapper;
import com.cs301p.easy_ecomm.responseClasses.CartItemDataResponse;
import com.cs301p.easy_ecomm.responseClasses.ShippingDetailsDataResponse;

@SpringBootApplication
public class EasyEcommApplication {
    private PlatformTransactionManager platformTransactionManager;
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(EasyEcommApplication.class, args);
        DAO_Factory dao_Factory = (DAO_Factory) applicationContext.getBean(DAO_Factory.class);

        Customer customer = new Customer(3, "Cust-3", "e.3@ecm.com", "psswd", "9182736450", "Madras, Tamil Nadu", 0);
        Wallet wallet = new Wallet(1, "1234-1211-3099-5656", (float)554.23);
        
        // MyApp myApp = new MyApp();
        MyApp myApp = (MyApp)applicationContext.getBean(MyApp.class);

        myApp.walletActions(customer, wallet, "link", dao_Factory);
    }
}

