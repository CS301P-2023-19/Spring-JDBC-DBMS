package com.cs301p.easy_ecomm;

import java.util.List;
import java.util.Scanner;

import com.cs301p.easy_ecomm.utilClasses.FilterBy;
import com.cs301p.easy_ecomm.utilClasses.OrderBy;
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
        CartItem cartItem = new CartItem(0, 0, 0);
        FilterBy filterBy = new FilterBy(false, "price", "0", "0");
        OrderBy orderBy = new OrderBy("", "");
        FilterBy filterByBlank = new FilterBy(true, "id", "0", "1000000");

        List<String> authParams = null;
        String q = null;

        Scanner scan = new Scanner(System.in);

        MyApp myApp = (MyApp) applicationContext.getBean(MyApp.class);
        AppMenu appMenu = new AppMenu();

        while (true) {
            String ch = null;
            System.out.println(
                    "=====================================================================================================================================");
            System.out.println(
                    "=====================================================================================================================================");
            System.out.println();

            if (!myApp.isLoggedIn) {
                q = appMenu.userMenu(scan);

                if (q.equals("quit") || q.equals("4")) {
                    scan.close();
                    System.exit(0);
                }
                if (q.equals("customer") || q.equals("seller") || q.equals("admin") || q.equals("1") || q.equals("2")
                        || q.equals("3")) {
                    authParams = appMenu.loginMenu(scan);
                }
            }

            int loginStatus = myApp.authActions(authParams.get(0), authParams.get(1), q, dao_Factory);

            if (loginStatus == 0) {

                if (myApp.isLoggedIn) {
                    System.out.println("Logged In!");
                    myApp.isLoggedIn = true;
                }

                if (q.equals("admin") || q.equals("1")) {
                    ch = appMenu.adminMenu(scan);

                    try {

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
                        } else if (ch.equals("list all customers") || ch.equals("5")) {
                            System.out.println("List of all customers:");
                        } else if (ch.equals("list all sellers") || ch.equals("6")) {
                            System.out.println("List of all sellers:");
                        } else if (ch.equals("logout") || ch.equals("7")) {
                            System.out.println("Logging out...");
                            myApp.isLoggedIn = false;
                        } else {
                            System.out.println("Invalid...");
                        }

                        if (myApp.isLoggedIn) {
                            myApp.adminActions(customer, seller, ch, dao_Factory);
                        }
                    } catch (Exception e) {
                        System.out.println("Something went wrong! Check validity of data...");
                    }

                } else if (q.equals("customer") || q.equals("2")) {
                    ch = appMenu.customerMenu(scan);
                    try {
                        if (ch.equals("list products") || ch.equals("1")) {
                            filterBy = filterByBlank;
                            System.out.println("Do you wish to filter the products (Yes/No)?");
                            String choice = scan.next();
                            choice = choice.strip().toLowerCase();
                            if (choice.equals("yes") || choice.equals("y")) {
                                System.out.println("Enter attribute to filter on(type/price)");
                                String filterChoice = scan.next();
                                filterChoice = filterChoice.strip().toLowerCase();
                                if (filterChoice.equals("type")) {
                                    String type;
                                    System.out.println("Enter the type:");
                                    type = scan.next();
                                    filterBy.setIsBetween(false);
                                    filterBy.setAttr("type");
                                    filterBy.setL_val(type);
                                } else if (filterChoice.equals("price")) {
                                    String lowerLimit, higherLimit;
                                    System.out.println("Enter the lower limit");
                                    lowerLimit = scan.next();
                                    System.out.println("Enter the higher limit");
                                    higherLimit = scan.next();
                                    System.out.println(higherLimit + "    " + lowerLimit);
                                    filterBy.setIsBetween(true);
                                    filterBy.setAttr("price");
                                    filterBy.setL_val(lowerLimit);
                                    filterBy.setH_val(higherLimit);
                                } else {
                                    System.out.println("Invalid attribute");
                                    filterBy = null;
                                }
                            } else if (choice.equals("no") || choice.equals("n")) {
                                filterBy = null;
                            } else {
                                System.out.println("Invalid choice, assuming no.");
                                filterBy = null;
                            }

                            System.out.println("Do you wish to sort the products (Yes/No)?");
                            choice = scan.next();
                            choice = choice.strip().toLowerCase();
                            if (choice.equals("yes") || choice.equals("y")) {
                                System.out.println("Enter attribute to sort on(quantity/price)");
                                String sortChoice = scan.next();
                                sortChoice = sortChoice.strip().toLowerCase();
                                if (sortChoice.equals("quantity")) {
                                    orderBy.setAttr("quantityAvailable");
                                } else if (sortChoice.equals("price")) {
                                    orderBy.setAttr("price");
                                }
                                System.out.println("Direction (asc/desc):");
                                String direction = scan.next().strip().toLowerCase();
                                orderBy.setDirection(direction);
                            } else if (choice.equals("no") || choice.equals("n")) {
                                orderBy.setAttr("id");
                                orderBy.setDirection("asc");
                            } else {
                                System.out.println("Invalid choice, assuming no.");
                                orderBy.setAttr("id");
                                orderBy.setDirection("asc");
                            }

                            myApp.listingActions(filterBy, orderBy, dao_Factory);

                        } else if (ch.equals("add product to cart") || ch.equals("2")) {
                            cartItem.setCustomerId(myApp.userCustomer.getId());
                            System.out.println("Enter the productId to be added:");
                            cartItem.setProductId(scan.nextInt());
                            System.out.println("Enter the quantity of the item to be added:");
                            cartItem.setQuantity(scan.nextInt());
                            myApp.cartItemActions(cartItem, ch, dao_Factory);
                        } else if (ch.equals("remove product from cart") || ch.equals("3")) {
                            cartItem.setCustomerId(myApp.userCustomer.getId());
                            System.out.println("Enter the productId to be removed:");
                            cartItem.setProductId(scan.nextInt());
                            myApp.cartItemActions(cartItem, ch, dao_Factory);
                        } else if (ch.equals("update product in cart") || ch.equals("4")) {
                            cartItem.setCustomerId(myApp.userCustomer.getId());
                            System.out.println("Enter the productId to be updated:");
                            cartItem.setProductId(scan.nextInt());
                            System.out.println("Enter the quantity of the item to be changed:");
                            cartItem.setQuantity(scan.nextInt());
                            myApp.cartItemActions(cartItem, ch, dao_Factory);
                        } else if (ch.equals("list cart items") || ch.equals("5")) {
                            cartItem.setCustomerId(myApp.userCustomer.getId());
                            myApp.cartItemActions(cartItem, ch, dao_Factory);
                        } else if (ch.equals("purchase products in cart") || ch.equals("6")) {
                            int ret = myApp.purchaseCart(myApp.userCustomer, dao_Factory);

                            if(ret > 0){
                                while (ret > 0) {
                                    System.out
                                            .println("Product with Id: " + ret + " will be discarded from cart!");
                                    String updateCart = "yes";
                                    product.setId(ret);
                                    myApp.handleInsufficientQuantity(myApp.userCustomer, product, updateCart,
                                            dao_Factory);
                                    ret = myApp.purchaseCart(myApp.userCustomer, dao_Factory);
                                }
                            }
                        } else if (ch.equals("review a product") || ch.equals("7")) {
                            int stars;
                            String content;
                            customer.setId(myApp.userCustomer.getId());
                            System.out.println("Enter the productId to review:");
                            product.setId(scan.nextInt());
                            System.out.println("Enter number of stars for the product: (0-5)");
                            stars = scan.nextInt();
                            System.out.println("Enter review description:");
                            scan.nextLine();
                            content = scan.nextLine();
                            myApp.reviewProduct(customer, product, stars, content, dao_Factory);
                        } else if (ch.equals("return a product") || ch.equals("8")) {
                            System.out.println("Enter productID of item to be returned");
                            product.setId(scan.nextInt());
                            myApp.returnProduct(myApp.userCustomer, product, dao_Factory);
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
                        } else if (ch.equals("logout") || ch.equals("11")) {
                            System.out.println("Logging out...");
                            myApp.isLoggedIn = false;
                        } else {
                            System.out.println("Invalid...........");
                        }
                    } catch (Exception e) {
                        System.out.println("Something went wrong! Check validity of data...");
                        System.out.println(e.getMessage());
                    }

                } else if (q.equals("seller") || q.equals("3")) {

                    ch = appMenu.sellerMenu(scan);
                    try {

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
                            myApp.walletActions(null, myApp.userSeller, walletS, "link wallet", dao_Factory);
                        } else if (ch.equals("update wallet") || ch.equals("5")) {
                            System.out.println("Enter credit card number to be associated with wallet:");
                            walletS.setCredit_card_no(scan.next());
                            System.out.println("Input new balance:");
                            walletS.setMoney(scan.nextFloat());
                            walletS.setId(myApp.userSeller.getWalletId());
                            myApp.walletActions(null, myApp.userSeller, walletS, "update wallet", dao_Factory);
                        } else if (ch.equals("logout") || ch.equals("6")) {
                            System.out.println("Logging out...");
                            myApp.isLoggedIn = false;
                        } else {
                            System.out.println("Invalid...........");
                        }
                    } catch (Exception e) {
                        System.out.println("Something went wrong! Check validity and type of data...");
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
            System.out.println("Press enter to continue");
            scan.nextLine();
        }

    }
}