package com.cs301p.easy_ecomm;

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
import com.cs301p.easy_ecomm.daoClasses.SellerDAO;
import com.cs301p.easy_ecomm.daoClasses.TransactionDAO;
import com.cs301p.easy_ecomm.daoClasses.WalletDAO;
import com.cs301p.easy_ecomm.entityClasses.CartItem;
import com.cs301p.easy_ecomm.entityClasses.Product;
import com.cs301p.easy_ecomm.entityClasses.Review;
import com.cs301p.easy_ecomm.entityClasses.Seller;
import com.cs301p.easy_ecomm.entityClasses.Transaction;
import com.cs301p.easy_ecomm.mappers.ShippingDetailsDataResponseMapper;
import com.cs301p.easy_ecomm.mappers.TransactionMapper;
import com.cs301p.easy_ecomm.mappers.WalletMapper;
import com.cs301p.easy_ecomm.responseClasses.CartItemDataResponse;
import com.cs301p.easy_ecomm.responseClasses.ShippingDetailsDataResponse;
import com.cs301p.easy_ecomm.utilClasses.FilterBy;
import com.cs301p.easy_ecomm.utilClasses.OrderBy;

public class MyApp {
    private PlatformTransactionManager platformTransactionManager;
    private JdbcTemplate jdbcTemplate;

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
                count = productDAO.addProduct(product);
                if (count > 0) {
                    System.out
                            .println("New product from seller with Id: " + product.getSellerId()
                                    + " added successfully!");
                }
                return (count);
            case "update product":
                count = productDAO.updateProduct(product);
                if (count > 0) {
                    System.out.println("Updated product with Id: " + product.getId());
                }
                return (count);
            case "remove product":
                count = productDAO.deleteProduct(product);
                if (count > 0) {
                    System.out.println("Removed product with Id: " + product.getId());
                }
                return (count);
            default:
                System.out.println("Invalid choice for this operation.");
                return (-1);// Error
        }
    }

    // Usecase (B)
    public int listingActions(FilterBy filterBy, OrderBy sortBy, DAO_Factory dao_Factory) {
        ProductDAO productDAO = dao_Factory.getProductDAO();
        List<Product> products = productDAO.listProducts(filterBy, sortBy);

        if (products == null) {
            System.out.println("No matching products found!");
            return (-1);
        }

        System.out.println(
                "-----------------------------------------------Requested products are:--------------------------------------------");
        for (Product product : products) {
            System.out.println(product);
            System.out.println();
        }
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
            case "link":
                System.out.println();
                System.out.println("Initiate multiple actions...");
                TransactionDefinition td = new DefaultTransactionDefinition();
                TransactionStatus ts = this.platformTransactionManager.getTransaction(td);
                try {
                    count = walletDAO.addWallet(wallet);
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
                w_query = new Wallet(customer.getWalletId(), "-", null);
                w = walletDAO.getWalletById(w_query);
                if (w == null) {
                    System.out.println("Wallet not found for given customer Id: " + customer.getId());
                    return ((float) -1);
                } else {
                    System.out.println("Current ballance: " + w.getMoney());
                    return (w.getMoney());
                }
            case "update":
                count = walletDAO.updateWallet(wallet);
                if (count > 0) {
                    System.out.println(
                            "Updated wallet with Id: " + wallet.getId() + "\nNew balance: " + wallet.getMoney());
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
        int count = -1;
        switch (choice.strip().toLowerCase()) {
            case "add":
                count = cartItemDAO.addCartItem(cartItem);
                if (count > 0) {
                    System.out
                            .println("New product with Id: " + cartItem.getProductId()
                                    + " added to cart by customer with Id: " + cartItem.getCustomerId());
                }
                return (count);
            case "update":
                count = cartItemDAO.updateCartItem(cartItem);
                if (count > 0) {
                    System.out.println("Updated product with Id: " + cartItem.getProductId()
                            + " in cart by customer with Id: " + cartItem.getCustomerId());
                }
                return (count);
            case "remove":
                count = cartItemDAO.deleteCartItem(cartItem);
                if (count > 0) {
                    System.out.println("Removed product with Id: " + cartItem.getProductId()
                            + " from cart by customer with Id: " + cartItem.getCustomerId());
                }
                return (count);
            case "list":
                Customer c = new Customer();
                c.setId(cartItem.getCustomerId());
                List<CartItemDataResponse> cartItemDataResponses = cartItemDAO.listCartItems(c);

                System.out.println(
                        "------------------------------------------------------------------------------------------");
                System.out.println("Cart of customer with Id: " + c.getId());
                for (CartItemDataResponse cartItemDataResponse : cartItemDataResponses) {
                    System.out.println();
                    System.out.println(cartItemDataResponse);
                    System.out.println();
                }
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
            // ! TODO: check wallet id of customer and seller to be not null.
            // Check cart of customer.
            List<CartItemDataResponse> cartItemDataResponses = cartItemDAO.listCartItems(customer);

            // Check Quantity.
            for (CartItemDataResponse p : cartItemDataResponses) {
                Product product = new Product();
                product.setId(p.getProductId());

                // Handle Insufficient Quantity.
                if (p.getQuantity() > productDAO.getProductById(product).getQuantityAvailable()) {
                    return (product.getId());
                }
            }

            // Generate new transactionId.
            String sql = "SELECT * FROM transaction WHERE id=(SELECT MAX(id) FROM transaction);";
            List<Transaction> transactions = this.jdbcTemplate.query(sql, new TransactionMapper());

            int new_id = transactions.get(0).getId() + 1;

            // Insert Transaction.
            for (CartItemDataResponse p : cartItemDataResponses) {
                int count = 0;

                Float currentBalance = walletActions(customer, null, null, "check", dao_Factory);

                if (currentBalance >= p.getPrice()) {
                    // Deduct money and delete from cart.
                    Wallet updateWallet = new Wallet(customer.getWalletId(), null, null);
                    updateWallet.setCredit_card_no(walletDAO.getWalletById(updateWallet).getCredit_card_no());
                    updateWallet.setMoney(currentBalance - p.getPrice());

                    walletActions(customer, null, updateWallet, "update", dao_Factory);

                    Product product_q = new Product(p.getProductId(), null, null, 0, (float) 0.00, 0);
                    Seller seller = productDAO.getSellerByProductId(product_q);
                    seller = sellerDAO.getSellerById(seller);

                    updateWallet.setId(seller.getWalletId());
                    updateWallet.setCredit_card_no(walletDAO.getWalletById(updateWallet).getCredit_card_no());
                    updateWallet.setMoney(currentBalance + p.getPrice());

                    walletActions(null, seller, updateWallet, "update", dao_Factory);

                    CartItem ci = new CartItem(customer.getId(), p.getProductId(), 0);
                    cartItemDAO.deleteCartItem(ci);

                    sql = "INSERT INTO transaction(id, customerId, sellerId, productId, date, returnStatus) VALUES (?, ?, ?, ?, ?, ?);";

                    Product product = new Product();
                    product.setId(p.getProductId());
                    product.setPrice(p.getPrice());
                    product.setQuantityAvailable(
                            productDAO.getProductById(product).getQuantityAvailable() - p.getQuantity());

                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = formatter.format(date);

                    count = this.jdbcTemplate.update(sql, new_id, customer.getId(),
                            productDAO.getProductById(product).getSellerId(),
                            p.getProductId(), formattedDate, false);

                    if (count <= 0) {
                        System.out.println("INSERT INTO TRANSACTION -- ERROR:\n productID: " + p.getProductId());
                    }

                    // Update quantity in products table.
                    productDAO.updateProduct(product);
                } else {
                    System.out.println("Insufficient balance for product Id: " + p.getProductId());
                }
            }

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
                    System.out.println("Added review for " + product.getId() + ", by " + customer.getId());
                    platformTransactionManager.commit(ts);
                    return (count);
                }
            }

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
            int count = 0;

            for (Transaction transaction : transactions) {
                if (transaction.getProductId() == product.getId()) {
                    // If yes, update transaction table, 7-day policy is checked internally.
                    Transaction updated_transaction = new Transaction(transaction.getId(), transaction.getCustomerId(),
                            transaction.getSellerId(), transaction.getSellerId(), transaction.getDate(), true);
                    int ret = transactionDAO.updateTransaction(updated_transaction);

                    if (ret > 0) {
                        System.out.println("Returned product with Id: " + product.getId() + ", by " + customer.getId());
                    }
                    platformTransactionManager.commit(ts);
                    return (count);
                }
            }
        } catch (Exception ex) {
            System.out.println("Transaction Failed: " + ex);
            platformTransactionManager.rollback(ts);
        }

        return (0); // Success
    }

    // ADDITIONAL: Get customer address of a particular transaction. (IMT2021055).
    public int getShippingAddress(Transaction transaction, DAO_Factory dao_Factory) {
        System.out.println();
        System.out.println("Initiate multiple actions...");
        TransactionDefinition td = new DefaultTransactionDefinition();
        TransactionStatus ts = this.platformTransactionManager.getTransaction(td);
        String sql;

        try {
            sql = "SELECT t.id, t.productId, c.name, c.address FROM transaction as t, customer as c WHERE t.customerId=c.id AND t.id="
                    + transaction.getId();
            List<ShippingDetailsDataResponse> shippingDetailsDataResponses = this.jdbcTemplate.query(sql,
                    new ShippingDetailsDataResponseMapper());

            String res = shippingDetailsDataResponses.get(0).getCustomerAddress();
            System.out
                    .println("Customer who made the transaction with Id: " + transaction.getId() + " lived at: " + res);
            platformTransactionManager.commit(ts);
        } catch (Exception ex) {
            System.out.println("Transaction Failed: " + ex);
            platformTransactionManager.rollback(ts);
        }

        return (0);
    }

    // Admin Actions.
    public int adminActions(Customer customer, Seller seller, String choice, DAO_Factory dao_Factory) {
        CustomerDAO customerDAO = dao_Factory.getCustomerDAO();
        SellerDAO sellerDAO = dao_Factory.getSellerDAO();
        switch (choice.strip().toLowerCase()) {
            case "add customer":
                customerDAO.addCustomer(customer);
                break;
            case "add seller":
                sellerDAO.addSeller(seller);
                break;
            case "remove customer":
                customerDAO.deleteCustomer(customer);
                break;
            case "remove seller":
                sellerDAO.deleteSeller(seller);
                break;
            case "list all customers":
                List <Customer> customers = customerDAO.getAllCustomers();
                for (Customer c : customers) {
                    System.out.println(c);
                }
                break;
            case "list all sellers":
                List <Seller> sellers = sellerDAO.getAllSellers();
                for (Seller s : sellers) {
                    System.out.println(s);
                }
                break;
            default:
                break;
        }

        return (0);
    }
}