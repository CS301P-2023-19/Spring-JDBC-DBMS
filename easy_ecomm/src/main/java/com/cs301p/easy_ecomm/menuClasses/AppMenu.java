package com.cs301p.easy_ecomm.menuClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppMenu {
    public String userMenu(Scanner scan) {
        System.out.println(
                "--------------------------------------------------------------User Menu--------------------------------------------------------------");
        System.out.println("Choose type of user: ");
        System.out.println("1.) Admin");
        System.out.println("2.) Customer");
        System.out.println("3.) Seller");
        System.out.println("4.) Quit");
        System.out.print("=> ");
        String q = scan.next().strip().toLowerCase();
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------------------");
        return (q);
    }

    public List<String> loginMenu(Scanner scan) {
        System.out.println(
                "--------------------------------------------------------------Login Menu-------------------------------------------------------------");
        System.out.println("Enter Email (or name for admin): ");
        System.out.print("=> ");
        String usrName = scan.next();
        System.out.println("Enter Password: ");
        System.out.print("=> ");
        String psswd = scan.next();
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------------------");

        List<String> l = new ArrayList<String>(0);
        l.add(usrName);
        l.add(psswd);
        return (l);
    }

    public String adminMenu(Scanner scan) {
        System.out.println(
                "--------------------------------------------------------------Admin Menu-------------------------------------------------------------");
        System.out.println("Choose Operation: ");
        System.out.println("1.) Add Customer");
        System.out.println("2.) Add Seller");
        System.out.println("3.) Remove Customer");
        System.out.println("4.) Remove Seller");
        System.out.println("5.) List All Customers");
        System.out.println("6.) List All Sellers");
        System.out.println("7.) Logout");
        // scan.nextLine();
        System.out.print("=> ");
        String ch = scan.nextLine().strip().toLowerCase();
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------------");
        return (ch);
    }

    public String customerMenu(Scanner scan) {
        System.out.println(
                "--------------------------------------------------------------Customer Menu-----------------------------------------------------------");
        System.out.println("Choose Operation: ");
        System.out.println("1.) List Products");
        System.out.println("2.) Add Product To Cart");
        System.out.println("3.) Remove Product From Cart");
        System.out.println("4.) Update Product In Cart");
        System.out.println("5.) List Cart Items");
        System.out.println("6.) Purchase Products In Cart");
        System.out.println("7.) Review A Product");
        System.out.println("8.) Return A Product");
        System.out.println("9.) Link Wallet");
        System.out.println("10.) Update Wallet");
        System.out.println("11.) Logout");
        // scan.nextLine();
        System.out.print("=> ");
        String ch = scan.nextLine().strip().toLowerCase();
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------------");
        return (ch);
    }

    public String sellerMenu(Scanner scan) {
        System.out.println(
                "--------------------------------------------------------------Seller Menu-------------------------------------------------------------");
        System.out.println("Choose Operation: ");
        System.out.println("1.) Add Product");
        System.out.println("2.) Remove Product");
        System.out.println("3.) Update Product");
        System.out.println("4.) Link Wallet");
        System.out.println("5.) Update Wallet");
        System.out.println("6.) Logout");
        // scan.nextLine();
        System.out.print("=> ");
        String ch = scan.nextLine().strip().toLowerCase();
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------------");
        return (ch);
    }
}
