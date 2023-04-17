package com.cs301p.easy_ecomm;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
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
import com.cs301p.easy_ecomm.entityClasses.Customer;
import com.cs301p.easy_ecomm.entityClasses.Product;
import com.cs301p.easy_ecomm.entityClasses.Review;
import com.cs301p.easy_ecomm.entityClasses.Transaction;
import com.cs301p.easy_ecomm.entityClasses.Wallet;
import com.cs301p.easy_ecomm.factoryClasses.DAO_Factory;
import com.cs301p.easy_ecomm.mappers.ShippingDetailsDataResponseMapper;
import com.cs301p.easy_ecomm.mappers.TransactionMapper;
import com.cs301p.easy_ecomm.responseClasses.CartItemDataResponse;
import com.cs301p.easy_ecomm.responseClasses.ShippingDetailsDataResponse;

@SpringBootApplication
public class EasyEcommApplication {
    private PlatformTransactionManager platformTransactionManager;
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(EasyEcommApplication.class, args);
        DAO_Factory dao_Factory = (DAO_Factory) applicationContext.getBean(DAO_Factory.class);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Usecase (A)
    // Direct call using productDAO.

    // Usecase (B)

    // Usecase (C)

    // Usecase (D)

    // Usecase (E) (IMT2021055)
    public int purchaseCart(Customer customer, DAO_Factory dao_Factory) {
        System.out.println();
        System.out.println("Initiate multiple actions...");
        TransactionDefinition td = new DefaultTransactionDefinition();
        CartItemDAO cartItemDAO = dao_Factory.getCartItemDAO();
        ProductDAO productDAO = dao_Factory.getProductDAO();
        TransactionStatus ts = this.platformTransactionManager.getTransaction(td);

        try {
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

                // return (count);
            }

            platformTransactionManager.commit(ts);
        } catch (Exception ex) {
            System.out.println("Transaction Failed: " + ex);
            platformTransactionManager.rollback(ts);
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

    // ! TODO: deactivate dao connections.
}
