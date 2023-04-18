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
            String ch = null;
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println(
                    "----------------------------------------------------------User Menu--------------------------------------------------------------");
            System.out.println("Choose type of user: ");
            System.out.println("1.) Admin");
            System.out.println("2.) Customer");
            System.out.println("3.) Seller");
            System.out.println("4.) Quit");
            System.out.print("=> ");
            String q = scan.next().strip().toLowerCase();
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------------------------");

            if (q.equals("quit")) {
                scan.close();
                System.exit(0);
            }

            System.out.println(
                    "----------------------------------------------------------Login Menu--------------------------------------------------------------");
            System.out.println("Enter Email (or name for admin): ");
            System.out.print("=> ");
            String usrName = scan.next();
            System.out.println("Enter Password: ");
            System.out.print("=> ");
            String psswd = scan.next();
            System.out.println(
                    "----------------------------------------------------------------------------------------------------------------------------------");

            // ! TODO Implement login.
            System.out.println(usrName +"-     -" + psswd);
            int loginStatus = myApp.authActions(usrName, psswd, q, dao_Factory);
            if(loginStatus == 0){
                System.out.println("Login Successful!");

                if (q.equals("admin")) {
                    System.out.println(
                            "----------------------------------------------------------Admin Menu--------------------------------------------------------------");
                    System.out.println("Choose Operation: ");
                    System.out.println("1.) Add Customer");
                    System.out.println("2.) Add Seller");
                    System.out.println("3.) Remove Customer");
                    System.out.println("4.) Remove Seller");
                    System.out.println("5.) List All Customers");
                    System.out.println("6.) List All Sellers");
                    scan.nextLine();
                    System.out.print("=> ");
                    ch = scan.nextLine().strip().toLowerCase();
                    System.out.println(
                            "----------------------------------------------------------------------------------------------------------------------------------");
    
                    if (ch.equals("add customer")) {
                        System.out.println(
                                "Input name, email, password, phone and address as continuous space-seperated strings:");
                        System.out.print("=> ");
                        customer.setName(scan.next());
                        customer.setEmail(scan.next());
                        customer.setPassword(scan.next());
                        customer.setPhone(scan.next());
                        customer.setAddress(scan.next());
                    } else if (ch.equals("add seller")) {
                        System.out.println(
                                "Input name, email, password and phone as continuous space-seperated strings:");
                        System.out.print("=> ");
                        seller.setName(scan.next());
                        seller.setEmail(scan.next());
                        seller.setPassword(scan.next());
                        seller.setPhone(scan.next());
                    } else if (ch.equals("remove customer")) {
                        System.out.println("Input customer Id to remove:");
                        System.out.print("=> ");
                        customer.setId(scan.nextInt());
                    } else if (ch.equals("remove seller")) {
                        System.out.println("Input seller Id to remove:");
                        System.out.print("=> ");
                        seller.setId(scan.nextInt());
                    } else if (ch.equals("list all customers")) {
                        System.out.println("List of all customers:");
                    } else if (ch.equals("list all sellers")) {
                        System.out.println("List of all sellers:");
                    } else {
                        System.out.println("Invalid...");
                    }
                    myApp.adminActions(customer, seller, ch, dao_Factory);
    
                } else if (q.equals("customer")) {
                    System.out.println(
                            "----------------------------------------------------------Customer Menu--------------------------------------------------------------");
                    System.out.println("Choose Operation: ");
                    System.out.println("1.) List Products");
                    System.out.println("2.) Add Product To Cart");
                    System.out.println("3.) Remove Product From Cart");
                    System.out.println("4.) Update Product In Cart");
                    System.out.println("5.) Purchase Products In Cart");
                    System.out.println("4.) Review A Product");
                    System.out.println("5.) Return A Product");
                    System.out.println("6.) Link Wallet");
                    System.out.println("7.) Update Wallet");
                    scan.nextLine();
                    System.out.print("=> ");
                    ch = scan.nextLine().strip().toLowerCase();
                    System.out.println(
                            "-------------------------------------------------------------------------------------------------------------------------------------");
    
                    if (ch.equals("list products")) {
    
                    } else if (ch.equals("add product to cart")) {
    
                    } else if (ch.equals("remove product from cart")) {
    
                    } else if (ch.equals("update product in cart")) {
    
                    } else if (ch.equals("purchase products in cart")) {
    
                    } else if (ch.equals("review a product")) {
    
                    } else if (ch.equals("return a product")) {
    
                    } else if (ch.equals("link wallet")) {
                    } else if (ch.equals("update wallet")) {
    
                    } else {
                        System.out.println("Invalid...........");
                    }
    
                } else if (q.equals("seller")) {
    
                    System.out.println(
                            "----------------------------------------------------------Seller Menu--------------------------------------------------------------");
                    System.out.println("Choose Operation: ");
                    System.out.println("1.) Add Product");
                    System.out.println("2.) Remove Product");
                    System.out.println("3.) Update Product");
                    System.out.println("4.) Link Wallet");
                    System.out.println("5.) Update Wallet");
                    scan.nextLine();
                    System.out.print("=> ");
                    ch = scan.nextLine().strip().toLowerCase();
                    System.out.println(
                            "-----------------------------------------------------------------------------------------------------------------------------------");
    
                    if (ch.equals("add product")) {
                        System.out.println(
                                "Input type, name, sellerId, price and quantity as continuous space-seperated strings:");
                        product.setType(scan.next());
                        product.setName(scan.next());
                        product.setSellerId(scan.nextInt());
                        product.setPrice(scan.nextFloat());
                        product.setQuantityAvailable(scan.nextInt());
                        myApp.sellerActions(product, ch, dao_Factory);
                    } else if (ch.equals("remove product")) {
                        System.out.println("Input product Id to remove:");
                        product.setId(scan.nextInt());
                        myApp.sellerActions(product, ch, dao_Factory);
                    } else if (ch.equals("update product")) {
                        System.out.println("Input product Id to update:");
                        product.setId(scan.nextInt());
                        System.out.println("Input updated price:");
                        product.setPrice(scan.nextFloat());
                        System.out.println("Input updated quantity:");
                        product.setQuantityAvailable(scan.nextInt());
                        myApp.sellerActions(product, ch, dao_Factory);
                    } else if (ch.equals("link wallet")) {
                    } else if (ch.equals("update wallet")) {
    
                    } else {
                        System.out.println("Invalid...........");
                    }
    
                } else {
                    System.out.println("Invalid choice");
                }

            }else{
                System.out.println("Login Unsuccessful! Try Again...");
            }

            System.out.println();
        }
    }
}
