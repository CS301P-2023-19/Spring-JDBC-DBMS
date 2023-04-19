package com.cs301p.easy_ecomm;

import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Product;
import com.cs301p.easy_ecomm.entityClasses.Seller;
import com.cs301p.easy_ecomm.entityClasses.Wallet;
import com.cs301p.easy_ecomm.factoryClasses.DAO_Factory;
import com.cs301p.easy_ecomm.menuClasses.AppMenu;

@SpringBootApplication
public class EasyEcommApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(EasyEcommApplication.class, args);
        DAO_Factory dao_Factory = (DAO_Factory) applicationContext.getBean(DAO_Factory.class);

        Customer customer = new Customer(0, "custName", "custEmail", "password", "phone", "address", 0);
        Seller seller = new Seller(0, "selName", "selEmail", "password", "phone", 0);
        Wallet walletC = new Wallet(0, "CCN", (float) 0.00);
        Wallet walletS = new Wallet(0, "CCN", (float) 0.00);
        Product product = new Product(0, "type", "name", 0, (float) 0.00, 0);

        boolean isLoggedIn = false;
        List<String> authParams = null;
        String q = null;

        Scanner scan = new Scanner(System.in);

        MyApp myApp = (MyApp) applicationContext.getBean(MyApp.class);
        AppMenu appMenu = new AppMenu();

        // ! TODO: allow users to update one value, retain others.
        while (true) {
            String ch = null;
            System.out.println(
                    "=====================================================================================================================================");
            System.out.println(
                    "=====================================================================================================================================");
            System.out.println();

            if (!isLoggedIn) {
                q = appMenu.userMenu(scan);

                if (q.equals("quit")) {
                    scan.close();
                    System.exit(0);
                }

                authParams = appMenu.loginMenu(scan);
            }

            int loginStatus = myApp.authActions(authParams.get(0), authParams.get(1), q, dao_Factory);
            if (loginStatus == 0) {
                System.out.println("Login Successful!");
                isLoggedIn = true;

                if (q.equals("admin")) {
                    ch = appMenu.adminMenu(scan);

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
                    } else if (ch.equals("logout")) {
                        System.out.println("Logging out...");
                        isLoggedIn = false;
                    } else {
                        System.out.println("Invalid...");
                    }

                    if (isLoggedIn) {
                        myApp.adminActions(customer, seller, ch, dao_Factory);
                    }

                } else if (q.equals("customer")) {
                    ch = appMenu.customerMenu(scan);

                    if (ch.equals("list products")) {

                    } else if (ch.equals("add product to cart")) {

                    } else if (ch.equals("remove product from cart")) {

                    } else if (ch.equals("update product in cart")) {

                    } else if (ch.equals("purchase products in cart")) {

                    } else if (ch.equals("review a product")) {

                    } else if (ch.equals("return a product")) {

                    } else if (ch.equals("link wallet")) {
                        System.out.println("Enter credit card number to be associated with wallet:");
                        walletC.setCredit_card_no(scan.next());
                        System.out.println("Input initial balance:");
                        walletC.setMoney(scan.nextFloat());
                        myApp.walletActions(myApp.userCustomer, null, walletC, ch, dao_Factory);
                    } else if (ch.equals("update wallet")) {
                        System.out.println("Enter credit card number to be associated with wallet:");
                        walletC.setCredit_card_no(scan.next());
                        System.out.println("Input new balance:");
                        walletC.setMoney(scan.nextFloat());
                        myApp.walletActions(myApp.userCustomer, null, walletC, ch, dao_Factory);
                    } else if (ch.equals("logout")) {
                        System.out.println("Logging out...");
                        isLoggedIn = false;
                    } else {
                        System.out.println("Invalid...........");
                    }

                } else if (q.equals("seller")) {

                    ch = appMenu.sellerMenu(scan);

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
                        System.out.println("Enter credit card number to be associated with wallet:");
                        walletS.setCredit_card_no(scan.next());
                        System.out.println("Input initial balance:");
                        walletS.setMoney(scan.nextFloat());
                        myApp.walletActions(null, myApp.userSeller, walletS, ch, dao_Factory);
                    } else if (ch.equals("update wallet")) {
                        System.out.println("Enter credit card number to be associated with wallet:");
                        walletS.setCredit_card_no(scan.next());
                        System.out.println("Input new balance:");
                        walletS.setMoney(scan.nextFloat());
                        walletS.setId(myApp.userSeller.getWalletId());
                        myApp.walletActions(null, myApp.userSeller, walletS, ch, dao_Factory);
                    } else if (ch.equals("logout")) {
                        System.out.println("Logging out...");
                        isLoggedIn = false;
                    } else {
                        System.out.println("Invalid...........");
                    }

                } else {
                    System.out.println("Invalid choice");
                }

            } else {
                System.out.println("Login Unsuccessful! Try Again...");
            }

            System.out.println();
            System.out.println(
                    "=====================================================================================================================================");
            System.out.println(
                    "=====================================================================================================================================");
        }
    }
}
