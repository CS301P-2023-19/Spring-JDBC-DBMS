package com.cs301p.easy_ecomm;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.transaction.PlatformTransactionManager;

import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Product;
import com.cs301p.easy_ecomm.entityClasses.Seller;
import com.cs301p.easy_ecomm.entityClasses.Wallet;
import com.cs301p.easy_ecomm.factoryClasses.DAO_Factory;

@SpringBootApplication
public class EasyEcommApplication {
    // private PlatformTransactionManager platformTransactionManager;
    // private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(EasyEcommApplication.class, args);
        DAO_Factory dao_Factory = (DAO_Factory) applicationContext.getBean(DAO_Factory.class);

        Customer customer = new Customer(0, "custName", "custEmail", "password", "phone", "address", 0);
        Seller seller = new Seller(0, "selName", "selEmail", "password", "phone", 0);
        Wallet walletC = new Wallet(0, "CCN", (float) 0.00);
        Wallet walletS = new Wallet(0, "CCN", (float) 0.00);
        Product product = new Product(0, "type", "name", 0, (float) 0.00, 0);

        Scanner scan = new Scanner(System.in);

        MyApp myApp = (MyApp) applicationContext.getBean(MyApp.class);

        while (true) {
            System.out.println();
            myApp.walletActions(customer, null, walletC, "link", dao_Factory);
            myApp.walletActions(null, seller, walletS, "link", dao_Factory);

            System.out.println("---------------User Menu-------------------");
            System.out.println("Choose type of user: ");
            System.out.println("1.) Admin");
            System.out.println("2.) Customer");
            System.out.println("3.) Seller");
            System.out.print("=> ");
            System.out.println("-------------------------------------------");

            String q = scan.next();

            System.out.println("---------------Login Menu-------------------");
            System.out.println("Enter Name: ");
            System.out.println("=> ");
            String usrName = scan.next();
            System.out.println("Enter Password: ");
            String psswd = scan.next();
            System.out.println("=> ");
            System.out.println("--------------------------------------------");

            // ! TODO Implement login.

            switch (q.strip().toLowerCase()) {
                case "admin":
                    System.out.println("---------------Admin Menu-------------------");
                    System.out.println("Choose type of user: ");
                    System.out.println("1.) Add Customer");
                    System.out.println("2.) Add Seller");
                    System.out.println("3.) Remove Customer");
                    System.out.println("4.) Remove Seller");
                    System.out.println("5.) List All Customers");
                    System.out.println("6.) List All Sellers");
                    System.out.println("--------------------------------------------");

                    q = scan.next();

                    switch (q.strip().toLowerCase()) {
                        case "add customer":

                            break;
                        case "add seller":

                            break;
                        case "remove customer":

                            break;
                        case "remove seller":

                            break;
                        case "list all customers":

                            break;
                        case "list all sellers":

                            break;
                        default:
                            break;
                    }

                    break;
                case "customer":
                    System.out.println("---------------Customer Menu-------------------");
                    System.out.println("Choose type of user: ");
                    System.out.println("1.) List Products");
                    System.out.println("2.) Add Product To Cart");
                    System.out.println("3.) Remove Product From Cart");
                    System.out.println("4.) Update Product In Cart");
                    System.out.println("5.) Purchase Products In Cart");
                    System.out.println("4.) Review A Product");
                    System.out.println("5.) Return A Product");
                    System.out.println("6.) Link Wallet");
                    System.out.println("7.) Update Wallet");
                    System.out.println("-----------------------------------------------");

                    q = scan.next();

                    switch (q.strip().toLowerCase()) {
                        case "list products":

                            break;
                        case "add product to cart":

                            break;
                        case "remove product from cart":

                            break;
                        case "update product in cart":

                            break;
                        case "purchase products in cart":

                            break;
                        case "review a product":

                            break;
                        case "return a product":

                            break;
                        case "link wallet":

                            break;
                        case "update wallet":

                            break;
                        default:
                            break;
                    }

                    break;
                case "seller":
                    System.out.println("---------------Seller Menu-------------------");
                    System.out.println("Choose type of user: ");
                    System.out.println("1.) Add Product");
                    System.out.println("2.) Remove Product");
                    System.out.println("3.) Update Product");
                    System.out.println("4.) Link Wallet");
                    System.out.println("5.) Update Wallet");
                    System.out.println("---------------------------------------------");

                    q = scan.next();

                    switch (q.strip().toLowerCase()) {
                        case "add product":

                            break;
                        case "remove product":

                            break;
                        case "update product":

                            break;
                        case "link wallet":

                            break;
                        case "update wallet":

                            break;
                        default:
                            break;
                    }

                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }

            System.out.println();
            if (q.equals("quit")) {
                System.exit(0);
            }
        }
    }
}
