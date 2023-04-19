package com.cs301p.easy_ecomm;

import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cs301p.easy_ecomm.entityClasses.CartItem;
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
        CartItem cartItem = new CartItem(0,0,0);

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

                if (q.equals("quit") || q.equals("4")) {
                    scan.close();
                    System.exit(0);
                }
                if(q.equals("customer") || q.equals("seller") || q.equals("admin") || q.equals("1") || q.equals("2") || q.equals("3")){
                    authParams = appMenu.loginMenu(scan);
                }
            }

            int loginStatus = myApp.authActions(authParams.get(0), authParams.get(1), q, dao_Factory);
            if (loginStatus == 0) {
                
                if(isLoggedIn==false){
                    System.out.println("Login Successful!");
                    isLoggedIn = true;
                }

                if (q.equals("admin") || q.equals("1")) {
                    ch = appMenu.adminMenu(scan);

                    if (ch.equals("add customer") || ch.equals("1")) {
                        System.out.println(
                                "Input name, email, password, phone and address as continuous space-seperated strings:");
                        System.out.print("=> ");
                        customer.setName(scan.next());
                        customer.setEmail(scan.next());
                        customer.setPassword(scan.next());
                        customer.setPhone(scan.next());
                        customer.setAddress(scan.next());
                    } else if (ch.equals("add seller") || ch.equals("2")) {
                        System.out.println(
                                "Input name, email, password and phone as continuous space-seperated strings:");
                        System.out.print("=> ");
                        seller.setName(scan.next());
                        seller.setEmail(scan.next());
                        seller.setPassword(scan.next());
                        seller.setPhone(scan.next());
                    } else if (ch.equals("remove customer") || ch.equals("3")) {
                        System.out.println("Input customer Id to remove:");
                        System.out.print("=> ");
                        customer.setId(scan.nextInt());
                    } else if (ch.equals("remove seller") || ch.equals("4")) {
                        System.out.println("Input seller Id to remove:");
                        System.out.print("=> ");
                        seller.setId(scan.nextInt());
                    } else if (ch.equals("list all customers")  || ch.equals("5")) {
                        System.out.println("List of all customers:");
                    } else if (ch.equals("list all sellers") || ch.equals("6")) {
                        System.out.println("List of all sellers:");
                    } else if (ch.equals("logout") || ch.equals("7")) {
                        System.out.println("Logging out...");
                        isLoggedIn = false;
                    } else {
                        System.out.println("Invalid...");
                    }

                    if (isLoggedIn) {
                        myApp.adminActions(customer, seller, ch, dao_Factory);
                    }

                } else if (q.equals("customer") || q.equals("2")) {
                    ch = appMenu.customerMenu(scan);

                    if (ch.equals("list products") || ch.equals("1")) {
                        myApp.listingActions(null, null, dao_Factory);
                        // Add sorting and filtering as well.
                    } else if (ch.equals("add product to cart") || ch.equals("2")) {
                        cartItem.setCustomerId(myApp.userCustomer.getId());
                        System.out.println("Please enter the productId to be added:");
                        cartItem.setProductId(scan.nextInt());
                        System.out.println("Please enter the quantity of the item to be added:");
                        cartItem.setQuantity(scan.nextInt());
                        myApp.cartItemActions(cartItem, "add", dao_Factory);
                    } else if (ch.equals("remove product from cart") || ch.equals("3")) {
                        cartItem.setCustomerId(myApp.userCustomer.getId());
                        System.out.println("Please enter the productId to be removed:");
                        cartItem.setProductId(scan.nextInt());
                        myApp.cartItemActions(cartItem,"remove", dao_Factory);
                    } else if (ch.equals("update product in cart") || ch.equals("4")) {
                        cartItem.setCustomerId(myApp.userCustomer.getId());
                        System.out.println("Please enter the productId to be updated:");
                        cartItem.setProductId(scan.nextInt());
                        System.out.println("Please enter the quantity of the item to be changed:");
                        cartItem.setQuantity(scan.nextInt());
                        myApp.cartItemActions(cartItem,"update", dao_Factory);
                    }else if(ch.equals("list cart items") || ch.equals("5")) {
                        cartItem.setCustomerId(myApp.userCustomer.getId());
                        myApp.cartItemActions(cartItem,"list", dao_Factory);
                    }else if (ch.equals("purchase products in cart") || ch.equals("6")) {
                        

                    } else if (ch.equals("review a product") || ch.equals("7")) {
                        int stars;
                        String content;
                        customer.setId(myApp.userCustomer.getId());
                        System.out.println("Please enter the productId to review:");
                        product.setId(scan.nextInt());
                        System.out.println("Please provide review stars for the product: (0-5)");
                        stars=scan.nextInt();
                        System.out.println("Enter the review description of the product:");
                        scan.nextLine();
                        content=scan.nextLine();
                        myApp.reviewProduct(customer, product, stars, content, dao_Factory);
                    } else if (ch.equals("return a product") || ch.equals("8")) {
                        customer.setId(myApp.userCustomer.getId());
                        System.out.println("Please enter productID of item to be returned");
                        product.setId(scan.nextInt());
                        myApp.returnProduct(customer,product,dao_Factory);
                    } else if (ch.equals("link wallet") || ch.equals("9")) {
                        System.out.println("Enter credit card number to be associated with wallet:");
                        walletC.setCredit_card_no(scan.next());
                        System.out.println("Input initial balance:");
                        walletC.setMoney(scan.nextFloat());
                        myApp.walletActions(myApp.userCustomer, null, walletC, ch, dao_Factory);
                    } else if (ch.equals("update wallet") || ch.equals("10")) {
                        System.out.println("Enter credit card number to be associated with wallet:");
                        walletC.setCredit_card_no(scan.next());
                        System.out.println("Input new balance:");
                        walletC.setMoney(scan.nextFloat());
                        myApp.walletActions(myApp.userCustomer, null, walletC, ch, dao_Factory);
                    } else if (ch.equals("logout")  || ch.equals("11")) {
                        System.out.println("Logging out...");
                        isLoggedIn = false;
                    } else {
                        System.out.println("Invalid...........");
                    }

                } else if (q.equals("seller") || q.equals("3")) {

                    ch = appMenu.sellerMenu(scan);

                    if (ch.equals("add product") || ch.equals("1")) {
                        System.out.println(
                                "Input type, name, price and quantity as continuous space-seperated strings:");
                        product.setType(scan.next());
                        product.setName(scan.next());
                        product.setSellerId(myApp.userSeller.getId());
                        product.setPrice(scan.nextFloat());
                        product.setQuantityAvailable(scan.nextInt());
                        myApp.sellerActions(product, ch, dao_Factory);
                    } else if (ch.equals("remove product") || ch.equals("2")) {
                        System.out.println("Input product Id to remove:");
                        product.setId(scan.nextInt());
                        myApp.sellerActions(product, ch, dao_Factory);
                    } else if (ch.equals("update product") || ch.equals("3")) {
                        System.out.println("Input product Id to update:");
                        product.setId(scan.nextInt());
                        System.out.println("Input updated price:");
                        product.setPrice(scan.nextFloat());
                        System.out.println("Input updated quantity:");
                        product.setQuantityAvailable(scan.nextInt());
                        myApp.sellerActions(product, ch, dao_Factory);
                    } else if (ch.equals("link wallet") || ch.equals("4")) {
                        System.out.println("Enter credit card number to be associated with wallet:");
                        walletS.setCredit_card_no(scan.next());
                        System.out.println("Input initial balance:");
                        walletS.setMoney(scan.nextFloat());
                        myApp.walletActions(null, myApp.userSeller, walletS, ch, dao_Factory);
                    } else if (ch.equals("update wallet") || ch.equals("5")) {
                        System.out.println("Enter credit card number to be associated with wallet:");
                        walletS.setCredit_card_no(scan.next());
                        System.out.println("Input new balance:");
                        walletS.setMoney(scan.nextFloat());
                        walletS.setId(myApp.userSeller.getWalletId());
                        myApp.walletActions(null, myApp.userSeller, walletS, ch, dao_Factory);
                    } else if (ch.equals("logout") || ch.equals("6")) {
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