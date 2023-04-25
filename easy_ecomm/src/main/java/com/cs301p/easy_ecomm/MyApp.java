package com.cs301p.easy_ecomm;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Wallet;
import com.cs301p.easy_ecomm.factoryClasses.DAO_Factory;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.cs301p.easy_ecomm.daoClasses.AdminDAO;
import com.cs301p.easy_ecomm.daoClasses.CartItemDAO;
import com.cs301p.easy_ecomm.daoClasses.CustomerDAO;
import com.cs301p.easy_ecomm.daoClasses.ProductDAO;
import com.cs301p.easy_ecomm.daoClasses.ReviewDAO;
import com.cs301p.easy_ecomm.daoClasses.SellerDAO;
import com.cs301p.easy_ecomm.daoClasses.TransactionDAO;
import com.cs301p.easy_ecomm.daoClasses.WalletDAO;
import com.cs301p.easy_ecomm.entityClasses.Admin;
import com.cs301p.easy_ecomm.entityClasses.CartItem;
import com.cs301p.easy_ecomm.entityClasses.Product;
import com.cs301p.easy_ecomm.entityClasses.Review;
import com.cs301p.easy_ecomm.entityClasses.Seller;
import com.cs301p.easy_ecomm.entityClasses.Transaction;
import com.cs301p.easy_ecomm.mappers.ShippingDetailsDataResponseMapper;
import com.cs301p.easy_ecomm.mappers.TransactionMapper;
import com.cs301p.easy_ecomm.mappers.WalletMapper;
import com.cs301p.easy_ecomm.responseClasses.CartItemDataResponse;
import com.cs301p.easy_ecomm.responseClasses.ReviewDataResponse;
import com.cs301p.easy_ecomm.responseClasses.ShippingDetailsDataResponse;
import com.cs301p.easy_ecomm.utilClasses.FilterBy;
import com.cs301p.easy_ecomm.utilClasses.OrderBy;

import de.vandermeer.asciitable.AsciiTable;

public class MyApp {
    private PlatformTransactionManager platformTransactionManager;
    private JdbcTemplate jdbcTemplate;

    public Seller userSeller;
    public Customer userCustomer;
    public Admin userAdmin;
    public boolean isLoggedIn = false;
    private AsciiTable asciiTable = null;

    public MyApp(PlatformTransactionManager platformTransactionManager, JdbcTemplate jdbcTemplate) {
        this.platformTransactionManager = platformTransactionManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Usecase (A) (IMT2021055)
    public int sellerActions(Product product, String choice, DAO_Factory dao_Factory) {
        ProductDAO productDAO = dao_Factory.getProductDAO();
        int count = -1;
        switch (choice.strip().toLowerCase()) {
            case "add product":
            case "1":
                count = productDAO.addProduct(product);
                if (count > 0) {
                    System.out
                            .println("New product from seller with Id: " + product.getSellerId()
                                    + " added successfully!");
                }
                return (count);
            case "update product":
            case "3":
                count = productDAO.updateProduct(product);
                if (count > 0) {
                    System.out.println("Updated product with Id: " + product.getId());
                }
                return (count);
            case "remove product":
            case "2":
                count = productDAO.deleteProduct(product);
                if (count > 0) {
                    System.out.println("Removed product with Id: " + product.getId());
                } else if (count == 0) {
                    System.out.println("Product to remove not found!");
                } else {
                    System.out.println(
                            "The product you are trying to remove has been purchased by customer(s)\nWe can not remove it from our records, setting its quantity and price to 0...");
                    product.setQuantityAvailable(0);
                    product.setPrice((float)0.00);        
                    count = productDAO.updateProduct(product);
                    if (count > 0) {
                        System.out.println("Updated product with Id: " + product.getId());
                    }
                }
                return (count);
            default:
                System.out.println("Invalid choice for this operation.");
                return (-1);// Error
        }
    }

    // Usecase (B) (IMT2021055)
    public int listingActions(FilterBy filterBy, OrderBy sortBy, DAO_Factory dao_Factory) {
        ProductDAO productDAO = dao_Factory.getProductDAO();
        List<Product> products = productDAO.listProducts(filterBy, sortBy);

        if (products == null) {
            System.out.println("No matching products found!");
            return (-1);
        }

        System.out.println(
                "-----------------------------------------------Requested products are:--------------------------------------------");
        this.asciiTable = new AsciiTable();
        this.asciiTable.addRule();
        this.asciiTable.addRow("id", "type", "name", "sellerId", "price", "quantityAvailable");
        this.asciiTable.addRule();
        for (Product product : products) {
            // System.out.println(product);
            this.asciiTable.addRow(product.getId(), product.getType(), product.getName(), product.getSellerId(),
                    product.getPrice(), product.getQuantityAvailable());
            this.asciiTable.addRule();
        }
        String output = this.asciiTable.render(120);
        System.out.println(output);
        this.asciiTable = null;
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------");

        return (0);
    }

    // Usecase (C) (IMT2021055)
    public Float walletActions(Customer customer, Seller seller, Wallet wallet, String choice,
            DAO_Factory dao_Factory) {
        WalletDAO walletDAO = dao_Factory.getWalletDAO();
        CustomerDAO customerDAO = dao_Factory.getCustomerDAO();
        SellerDAO sellerDAO = dao_Factory.getSellerDAO();
        int count = -1;
        Wallet w_query;
        Wallet w = null;

        switch (choice.strip().toLowerCase()) {
            case "link wallet":
            case "9":
                System.out.println();
                System.out.println("Initiate multiple actions...");
                TransactionDefinition td = new DefaultTransactionDefinition();
                TransactionStatus ts = this.platformTransactionManager.getTransaction(td);
                try {
                    count = walletDAO.addWallet(wallet);
                    if (!(count > 0)) {
                        System.out
                                .println("Linking unsuccessful! Ensure that credit card number is not already in use!");
                        return ((float) -1);
                    }
                    if (count > 0) {
                        // Generate new transactionId.
                        String sql = "SELECT * FROM wallet WHERE id=(SELECT MAX(id) FROM wallet);";
                        List<Wallet> wallets = this.jdbcTemplate.query(sql, new WalletMapper());

                        int new_id = wallets.get(0).getId();
                        System.out.println("Wallet added with Id: " + new_id);

                        if (customer != null) {
                            customer.setWalletId(new_id);
                            int cnt = customerDAO.updateCustomer(customer);
                            if (cnt > 0) {
                                System.out.println(
                                        "Linked wallet Id: " + new_id + " to customer Id: " + customer.getId());
                                platformTransactionManager.commit(ts);
                            }
                        } else {
                            seller.setWalletId(new_id);
                            int cnt = sellerDAO.updateSeller(seller);
                            if (cnt > 0) {
                                System.out.println(
                                        "Linked wallet Id: " + new_id + " to seller Id: " + seller.getId());
                                platformTransactionManager.commit(ts);
                            }
                        }

                    } else {
                        System.out.println("Wallet add failed!");
                    }
                } catch (Exception ex) {
                    System.out.println("Transaction Failed: " + ex);
                    platformTransactionManager.rollback(ts);
                    return ((float) -1);
                }
                break;
            case "check":
                w_query = new Wallet(-1, null, null);
                if (customer != null) {
                    w_query = new Wallet(customer.getWalletId(), "-", null);
                } else if (seller != null) {
                    w_query = new Wallet(seller.getWalletId(), "-", null);
                }
                w = walletDAO.getWalletById(w_query);
                if (w == null) {
                    System.out.println("Wallet not found for given customer Id: " + customer.getId());
                    return ((float) -1);
                } else {
                    System.out.println("Current ballance: " + w.getMoney());
                    return (w.getMoney());
                }
            case "update wallet":
            case "10":
                if (customer != null) {
                    wallet.setId(customer.getWalletId());
                } else if (seller != null) {
                    wallet.setId(seller.getWalletId());
                }
                count = walletDAO.updateWallet(wallet);
                if (count > 0) {
                    System.out.println(
                            "Updated wallet with Id: " + wallet.getId() + "\nNew balance: " + wallet.getMoney());
                } else {
                    System.out.println(
                            "Unable to update wallet, credit card number may already be in use or data provided is invalid");
                }
                break;
            default:
                System.out.println("Invalid choice for this operation.");
                return ((float) -1);// Error
        }

        return ((float) 0);
    }

    // Usecase (D) (IMT2021055)
    public int cartItemActions(CartItem cartItem, String choice, DAO_Factory dao_Factory) {
        CartItemDAO cartItemDAO = dao_Factory.getCartItemDAO();
        ProductDAO productDAO = dao_Factory.getProductDAO();
        Product product = new Product();
        product.setId(cartItem.getProductId());
        int count = -1;
        switch (choice.strip().toLowerCase()) {
            case "add product to cart":
            case "2":
                product = productDAO.getProductById(product);
                if (product != null) {
                    if (product.getQuantityAvailable() >= cartItem.getQuantity()) {
                        count = cartItemDAO.addCartItem(cartItem);
                        if (count > 0) {
                            System.out
                                    .println("New product with Id: " + cartItem.getProductId()
                                            + " added to cart by customer with Id: " + cartItem.getCustomerId());
                            return (count);
                        } else {
                            System.out.println("Unable to add product to cart!");
                            return (-1);
                        }
                    } else {
                        System.out.println(
                                "Insufficient quantity of product with Id: " + product.getId() + " available.");
                        return (-1);
                    }
                } else {
                    return (-1);
                }
            case "update product in cart":
            case "4":
                product = productDAO.getProductById(product);
                if (product != null) {
                    if (product.getQuantityAvailable() >= cartItem.getQuantity()) {
                        count = cartItemDAO.updateCartItem(cartItem);
                        if (count > 0) {
                            System.out.println("Updated product with Id: " + cartItem.getProductId()
                                    + " in cart by customer with Id: " + cartItem.getCustomerId());
                            return (count);
                        } else {
                            System.out.println("Unable to update cart for product with Id: " + product.getId()
                                    + ", ensure that the product is present in cart and correct data is provided.");
                        }
                    } else {
                        System.out.println(
                                "Insufficient quantity of product with Id: " + product.getId() + " available.");
                        return (-1);
                    }
                    return (count);
                }
            case "remove product from cart":
            case "3":
                count = cartItemDAO.deleteCartItem(cartItem);
                if (count > 0) {
                    System.out.println("Removed product with Id: " + cartItem.getProductId()
                            + " from cart by customer with Id: " + cartItem.getCustomerId());
                } else {
                    System.out.println("Could not remove product from cart, it may not be present.");
                }
                return (count);
            case "list cart items":
            case "5":
                Customer c = new Customer();
                c.setId(cartItem.getCustomerId());
                List<CartItemDataResponse> cartItemDataResponses = cartItemDAO.listCartItems(c);
                if (cartItemDataResponses == null) {
                    System.out.println("Cart of customer with customer ID : " + c.getId() + " is empty.");
                    return (-1);
                }

                System.out.println(
                        "------------------------------------------------------------------------------------------");
                System.out.println("Cart of customer with Id: " + c.getId());
                this.asciiTable = new AsciiTable();
                this.asciiTable.addRule();
                this.asciiTable.addRow("productId", "name", "price", "quantity");
                this.asciiTable.addRule();
                for (CartItemDataResponse cartItemDataResponse : cartItemDataResponses) {
                    // System.out.println();
                    // System.out.println(cartItemDataResponse);
                    // System.out.println();
                    this.asciiTable.addRow(cartItemDataResponse.getProductId(), cartItemDataResponse.getName(),
                            cartItemDataResponse.getPrice(), cartItemDataResponse.getQuantity());
                    this.asciiTable.addRule();
                }
                String output = this.asciiTable.render();
                System.out.println(output);
                this.asciiTable = null;
                System.out.println(
                        "------------------------------------------------------------------------------------------");

                return (cartItemDataResponses.size());
            default:
                System.out.println("Invalid choice for this operation.");
                return (-1);// Error
        }
    }

    // Usecase (E) (IMT2021055)
    public int purchaseCart(Customer customer, DAO_Factory dao_Factory) {
        System.out.println();
        System.out.println("Initiate multiple actions...");
        TransactionDefinition td = new DefaultTransactionDefinition();
        CartItemDAO cartItemDAO = dao_Factory.getCartItemDAO();
        ProductDAO productDAO = dao_Factory.getProductDAO();
        WalletDAO walletDAO = dao_Factory.getWalletDAO();
        SellerDAO sellerDAO = dao_Factory.getSellerDAO();
        TransactionStatus ts = this.platformTransactionManager.getTransaction(td);

        try {
            if (!(customer.getWalletId() > 0)) {
                System.out.println("Customer must link a wallet before purchase!");
                platformTransactionManager.rollback(ts);
                return (-1);
            }
            // Check cart of customer.
            List<CartItemDataResponse> cartItemDataResponses = cartItemDAO.listCartItems(customer);

            if (cartItemDataResponses == null) {
                System.out.println("Your cart is empty!");
                platformTransactionManager.rollback(ts);
                return (-2);
            }

            // Check Quantity.
            for (CartItemDataResponse p : cartItemDataResponses) {
                Product product = new Product();
                product.setId(p.getProductId());
                int availableQuant = productDAO.getProductById(product).getQuantityAvailable();

                // Handle Insufficient Quantity.
                if (p.getQuantity() > availableQuant) {
                    System.out.println("Insufficient quantity for productId: " + p.getProductId() + ", "
                            + availableQuant + " units are available.");
                    platformTransactionManager.rollback(ts);
                    return (product.getId());
                }
            }

            // Generate new transactionId.
            String sql = "SELECT * FROM transaction WHERE id=(SELECT MAX(id) FROM transaction);";
            List<Transaction> transactions = this.jdbcTemplate.query(sql, new TransactionMapper());
            int new_id = -1;

            if (transactions.size() == 0) {
                new_id = 1;
            } else {
                new_id = transactions.get(0).getId() + 1;
            }

            // Insert Transaction.
            for (CartItemDataResponse p : cartItemDataResponses) {
                int count = 0;

                Float currentBalance = walletActions(customer, null, null, "check", dao_Factory);

                if (currentBalance >= p.getPrice() * p.getQuantity()) {
                    // Deduct money and delete from cart.
                    Wallet updateWallet = new Wallet(customer.getWalletId(), null, null);
                    updateWallet.setCredit_card_no(walletDAO.getWalletById(updateWallet).getCredit_card_no());
                    updateWallet.setMoney(currentBalance - p.getPrice() * p.getQuantity());

                    walletActions(customer, null, updateWallet, "update wallet", dao_Factory);

                    Product product_q = new Product(p.getProductId(), null, null, 0, (float) 0.00, 0);
                    Seller seller = productDAO.getSellerByProductId(product_q);

                    seller = sellerDAO.getSellerById(seller);

                    if (seller == null) {
                        System.out.println("Seller offering the product not found!");
                        platformTransactionManager.rollback(ts);
                        return (-1);
                    }

                    if (!(seller.getWalletId() > 0)) {
                        System.out.println("Wait! Seller does not have a linked wallet\nPlease remove product with Id: "
                                + p.getProductId() + " from cart.");
                        platformTransactionManager.rollback(ts);
                        return (p.getProductId());
                    }

                    currentBalance = walletActions(null, seller, null, "check", dao_Factory);
                    updateWallet.setId(seller.getWalletId());
                    updateWallet.setCredit_card_no(walletDAO.getWalletById(updateWallet).getCredit_card_no());
                    updateWallet.setMoney(currentBalance + p.getPrice() * p.getQuantity());

                    walletActions(null, seller, updateWallet, "update wallet", dao_Factory);

                    CartItem ci = new CartItem(customer.getId(), p.getProductId(), 0);
                    cartItemDAO.deleteCartItem(ci);

                    sql = "INSERT INTO transaction(id, customerId, sellerId, productId, date, returnStatus) VALUES (?, ?, ?, ?, ?, ?);";

                    Product product = new Product();
                    product.setId(p.getProductId());
                    product.setPrice(p.getPrice());
                    product.setQuantityAvailable(
                            productDAO.getProductById(product).getQuantityAvailable() - p.getQuantity());

                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = formatter.format(date);

                    count = this.jdbcTemplate.update(sql, new_id, customer.getId(),
                            productDAO.getProductById(product).getSellerId(),
                            p.getProductId(), formattedDate, false);

                    if (count <= 0) {
                        System.out.println("INSERT INTO TRANSACTION -- ERROR:\n productID: " + p.getProductId());
                        platformTransactionManager.rollback(ts);
                        return (-3);
                    }

                    // Update quantity in products table.
                    productDAO.updateProduct(product);
                } else {
                    System.out.println("Insufficient balance for product Id: " + p.getProductId());
                    System.out.println("Please remove it from cart.");
                    platformTransactionManager.rollback(ts);
                    return (p.getProductId());
                }
            }
            System.out.println("Committing...");
            platformTransactionManager.commit(ts);
        } catch (Exception ex) {
            System.out.println("Transaction Failed: " + ex);
            platformTransactionManager.rollback(ts);
            return (-1);
        }
        return (0); // Success.
    }

    public int handleInsufficientQuantity(Customer customer, Product product, String choice, DAO_Factory dao_Factory) {
        // If choice = "yes", discard item, else buy reduced quantity.
        if (choice.strip().toLowerCase().equals("yes")) {
            // Delete from cart_item table.
            CartItemDAO cartItemDAO = dao_Factory.getCartItemDAO();
            CartItem c = new CartItem(customer.getId(), product.getId(), 0);
            int count = cartItemDAO.deleteCartItem(c);

            System.out.println("Deleted " + count + " rows from cart_item table.");
        } else {
            // Update quantity in cart.
            CartItemDAO cartItemDAO = dao_Factory.getCartItemDAO();
            CartItem c = new CartItem(customer.getId(), product.getId(), product.getQuantityAvailable());
            int count = cartItemDAO.updateCartItem(c);

            System.out.println("Updated " + count + " rows from cart_item table.");
        }

        return (0);
    }

    // Usecase (F) (IMT2021055)
    public int reviewProduct(Customer customer, Product product, int stars, String content, DAO_Factory dao_Factory) {
        System.out.println();
        System.out.println("Initiate multiple actions...");
        TransactionDefinition td = new DefaultTransactionDefinition();
        TransactionStatus ts = this.platformTransactionManager.getTransaction(td);

        // Check number of stars.
        if (stars > 5 || stars < 0) {
            System.out.println("Stars must be between 0 and 5");
            return (-2); // Error.
        }

        try {
            // Check if customer has purchased the product.
            TransactionDAO transactionDAO = dao_Factory.getTransactionDAO();
            List<Transaction> transactions = transactionDAO.getTransactionsByCustomer(customer);
            int count = 0;

            for (Transaction transaction : transactions) {
                if (transaction.getProductId() == product.getId()) {
                    // If yes, add review.
                    ReviewDAO reviewDAO = dao_Factory.getReviewDAO();
                    Review review = new Review(0, customer.getId(), product.getId(), stars, content);
                    count = reviewDAO.addReview(review);
                    if (count > 0) {
                        System.out.println("Added review for " + product.getId() + ", by " + customer.getId());
                    } else {
                        System.out.println("Unable to add review for specified product");
                    }
                    break;
                }
            }
            platformTransactionManager.commit(ts);
            return (count);
        } catch (Exception ex) {
            System.out.println("Transaction Failed: " + ex);
            platformTransactionManager.rollback(ts);
        }

        System.out.println("You can not write a review for a product which you haven't purchased!");
        return (-1); // Error.
    }

    // Usecase (G) (IMT2021055)
    public int returnProduct(Customer customer, Product product, DAO_Factory dao_Factory) {
        System.out.println();
        System.out.println("Initiate multiple actions...");
        TransactionDefinition td = new DefaultTransactionDefinition();
        TransactionStatus ts = this.platformTransactionManager.getTransaction(td);
        try {
            // Check if customer has purchased the product.
            TransactionDAO transactionDAO = dao_Factory.getTransactionDAO();
            List<Transaction> transactions = transactionDAO.getTransactionsByCustomer(customer);
            int count = -1;
            int cnt = 0;

            for (Transaction transaction : transactions) {
                if (transaction.getProductId() == product.getId()) {
                    cnt++;
                    // If yes, update transaction table, 7-day policy is checked internally, check
                    // if already returned.
                    if (!transaction.getReturnStatus()) {

                        // Check 7-day return policy.
                        LocalDate dateDB = transaction.getDate().toLocalDate();
                        LocalDate dateSS = LocalDate.now();
                        long days = ChronoUnit.DAYS.between(dateDB, dateSS);

                        if (days <= 7) {
                            String sql = "UPDATE transaction SET returnStatus=? WHERE customerId=? AND productId=? AND id=?;";
                            count = this.jdbcTemplate.update(sql, true,
                                    transaction.getCustomerId(),
                                    transaction.getProductId(), transaction.getId());
                            if (count > 0) {
                                System.out.println(
                                        "Returned product with Id: " + product.getId() + ", by " + customer.getId());
                            } else {
                                System.out.println("Unable to return product, check with seller.");
                            }
                        } else {
                            System.out.println("Too late to return the product.");
                            return (-1);
                        }

                    } else {
                        System.out.println("Product with Id: " + transaction.getProductId() + ", bought on: "
                                + transaction.getDate() + " was returned earlier!");
                    }
                }
            }

            if (cnt == 0) {
                System.out.println("Can not return a product which hasn't been purchased!");
                platformTransactionManager.rollback(ts);
                return (-1);
            }

            platformTransactionManager.commit(ts);
            return (0);
        } catch (Exception ex) {
            System.out.println("Transaction Failed: " + ex.getMessage());
            platformTransactionManager.rollback(ts);
            return (-1);
        }

    }

    // ADDITIONAL: Get current customer address of a particular transaction. (IMT2021055).
    public int getShippingAddress(Transaction transaction, DAO_Factory dao_Factory) {
        System.out.println();
        System.out.println("Initiate multiple actions...");
        TransactionDefinition td = new DefaultTransactionDefinition();
        TransactionStatus ts = this.platformTransactionManager.getTransaction(td);
        String sql;

        try {
            sql = "SELECT t.id, t.productId, c.name, c.address, c.phone FROM transaction as t, customer as c WHERE t.customerId=c.id AND t.id="
                    + transaction.getId();
            List<ShippingDetailsDataResponse> shippingDetailsDataResponses = this.jdbcTemplate.query(sql,
                    new ShippingDetailsDataResponseMapper());

            if (shippingDetailsDataResponses.size() == 0) {
                System.out.println("No matching records found!");
            }

            String res = shippingDetailsDataResponses.get(0).getCustomerAddress();
            String ph = shippingDetailsDataResponses.get(0).getPhone();
            String custName = shippingDetailsDataResponses.get(0).getCustomerName();
            System.out
                    .println("Customer, " + custName + "who made the transaction with Id: " + transaction.getId()
                            + " lived at: " + res + ", phone: " + ph);
            platformTransactionManager.commit(ts);
        } catch (Exception ex) {
            System.out.println("Transaction Failed: " + ex);
            platformTransactionManager.rollback(ts);
        }

        return (0);
    }

    // Review Actions. (IMT2021055)
    public int reviewActions(Customer customer, Product product, String choice, DAO_Factory dao_Factory) {
        ReviewDAO reviewDAO = dao_Factory.getReviewDAO();
        List<ReviewDataResponse> reviews;
        String output = "";
        switch (choice.strip().toLowerCase()) {
            case "view reviews by product":
            case "12":
                reviews = reviewDAO.getReviewsByProduct(product);

                this.asciiTable = new AsciiTable();
                this.asciiTable.addRule();
                this.asciiTable.addRow("id", "productId", "customerName", "productName", "stars (0-5)", "content");
                this.asciiTable.addRule();

                for (ReviewDataResponse r : reviews) {
                    this.asciiTable.addRow(r.getId(), r.getProductId(), r.getCustomerName(), r.getProductName(),
                            r.getStars(),
                            r.getContent());
                    this.asciiTable.addRule();
                }
                output = this.asciiTable.render();
                System.out.println(output);
                this.asciiTable = null;

                break;

            case "view your reviews":
            case "11":
                reviews = reviewDAO.getReviewsByCustomer(customer);

                this.asciiTable = new AsciiTable();
                this.asciiTable.addRule();
                this.asciiTable.addRow("id", "productId", "customerName", "productName", "stars (0-5)", "content");
                this.asciiTable.addRule();

                for (ReviewDataResponse r : reviews) {
                    this.asciiTable.addRow(r.getId(), r.getProductId(), r.getCustomerName(), r.getProductName(),
                            r.getStars(),
                            r.getContent());
                    this.asciiTable.addRule();
                }
                output = this.asciiTable.render();
                System.out.println(output);
                this.asciiTable = null;
                break;
        }
        return (0);
    }

    // Admin Actions. (IMT2021055)
    public int adminActions(Customer customer, Seller seller, String choice, DAO_Factory dao_Factory) {
        CustomerDAO customerDAO = dao_Factory.getCustomerDAO();
        SellerDAO sellerDAO = dao_Factory.getSellerDAO();
        int count = -1;
        String output = "";
        switch (choice.strip().toLowerCase()) {
            case "add customer":
            case "1":
                count = customerDAO.addCustomer(customer);
                if (count > 0) {
                    System.out.println("Added customer with email: " + customer.getEmail());
                } else {
                    System.out.println(
                            "Can not add customer, either a duplicate email is present or invalid data provided.");
                }
                break;
            case "add seller":
            case "2":
                count = sellerDAO.addSeller(seller);
                if (count > 0) {
                    System.out.println("Added seller with email: " + customer.getEmail());
                } else {
                    System.out.println(
                            "Can not add seller, either a duplicate email is present or invalid data provided.");
                }
                break;
            case "remove customer":
            case "3":
                count = customerDAO.deleteCustomer(customer);
                if (count > 0) {
                    System.out.println("Removed customer with Id: " + customer.getId());
                } else {
                    System.out.println(
                            "Can not remove customer, no match found.");
                }
                break;
            case "remove seller":
            case "4":
                count = sellerDAO.deleteSeller(seller);
                if (count > 0) {
                    System.out.println("Removed seller with email: " + seller.getId());
                } else {
                    System.out.println(
                            "Can not remove seller, no match found.");
                }
                break;
            case "list all customers":
            case "5":
                List<Customer> customers = customerDAO.getAllCustomers();
                this.asciiTable = new AsciiTable();
                this.asciiTable.addRule();
                this.asciiTable.addRow("id", "name", "email", "address", "phone", "walletId");
                this.asciiTable.addRule();
                for (Customer c : customers) {
                    // System.out.println(c);
                    this.asciiTable.addRow(c.getId(), c.getName(), c.getEmail(), c.getAddress(), c.getPhone(),
                            c.getWalletId());
                    this.asciiTable.addRule();
                }
                output = this.asciiTable.render();
                System.out.println(output);
                this.asciiTable = null;
                break;
            case "list all sellers":
            case "6":
                List<Seller> sellers = sellerDAO.getAllSellers();
                this.asciiTable = new AsciiTable();
                this.asciiTable.addRule();
                this.asciiTable.addRow("id", "name", "email", "phone", "walletId");
                this.asciiTable.addRule();
                for (Seller s : sellers) {
                    // System.out.println(s);
                    this.asciiTable.addRow(s.getId(), s.getName(), s.getEmail(), s.getPhone(), s.getWalletId());
                    this.asciiTable.addRule();
                }
                output = this.asciiTable.render();
                System.out.println(output);
                this.asciiTable = null;
                break;
            default:
                break;
        }

        return (0);
    }

    // Auth Actions (IMT2021055)
    public int authActions(String email, String password, String userType, DAO_Factory dao_Factory) {
        userAdmin = null;
        userCustomer = null;
        userSeller = null;
        this.isLoggedIn = false;

        switch (userType) {
            case "customer":
            case "2":
                Customer customer = new Customer();
                customer.setEmail(email);
                CustomerDAO customerDAO = dao_Factory.getCustomerDAO();
                customer = customerDAO.getCustomerByEmail(customer);

                if (customer == null) {
                    userCustomer = null;
                    return (-1);
                } else {
                    if (customer.getEmail().equals(email) && customer.getPassword().equals(password)) {
                        userCustomer = customer;
                        this.isLoggedIn = true;
                        return (0);
                    } else {
                        userCustomer = null;
                        return (-1);
                    }
                }
            case "seller":
            case "3":
                Seller seller = new Seller();
                seller.setEmail(email);
                SellerDAO sellerDAO = dao_Factory.getSellerDAO();
                seller = sellerDAO.getSellerByEmail(seller);

                if (seller == null) {
                    userSeller = null;
                    return (-1);
                } else {
                    if (seller.getEmail().equals(email) && seller.getPassword().equals(password)) {
                        userSeller = seller;
                        this.isLoggedIn = true;
                        return (0);
                    } else {
                        userSeller = null;
                        return (-1);
                    }
                }
            case "admin":
            case "1":
                Admin admin = new Admin();
                admin.setA_name(email);
                admin.setA_password(password);
                AdminDAO adminDAO = dao_Factory.getAdminDAO();
                admin = adminDAO.getAdminByName(admin);
                if (admin == null) {
                    userAdmin = null;
                    return (-1);
                } else {
                    if (admin.getA_name().equals(email) && admin.getA_password().equals(password)) {
                        userAdmin = admin;
                        this.isLoggedIn = true;
                        return (0);
                    } else {
                        userAdmin = null;
                        return (-1);
                    }
                }
            default:
                this.isLoggedIn = false;
                userAdmin = null;
                userSeller = null;
                userCustomer = null;
                return (-1);
        }
    }
}
