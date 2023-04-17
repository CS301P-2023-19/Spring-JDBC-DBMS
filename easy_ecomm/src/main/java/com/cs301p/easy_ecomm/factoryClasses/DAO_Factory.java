package com.cs301p.easy_ecomm.factoryClasses;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.cs301p.easy_ecomm.daoClasses.AdminDAO;
import com.cs301p.easy_ecomm.daoClasses.CartItemDAO;
import com.cs301p.easy_ecomm.daoClasses.CustomerDAO;
import com.cs301p.easy_ecomm.daoClasses.ProductDAO;
import com.cs301p.easy_ecomm.daoClasses.ReviewDAO;
import com.cs301p.easy_ecomm.daoClasses.SellerDAO;
import com.cs301p.easy_ecomm.daoClasses.TransactionDAO;
import com.cs301p.easy_ecomm.daoClasses.WalletDAO;

public class DAO_Factory {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager platformTransactionManager;

    private AdminDAO adminDAO;
    private CartItemDAO cartItemDAO;
    private CustomerDAO customerDAO;
    private ProductDAO productDAO;
    private ReviewDAO reviewDAO;
    private SellerDAO sellerDAO;
    private TransactionDAO transactionDAO;
    private WalletDAO walletDAO;

    public DAO_Factory() {
    }

    public DAO_Factory(DataSource dataSource, JdbcTemplate jdbcTemplate,
            PlatformTransactionManager platformTransactionManager) {
                this.dataSource = dataSource;
                this.jdbcTemplate = jdbcTemplate;
                this.platformTransactionManager = platformTransactionManager;
    }

    // public void activateConnection() throws Exception
	// {
		// if( activeConnection == true )
		// 	throw new Exception("Connection already active");

		// System.out.println("Connecting to database...");
		// try{
		// 	Class.forName("com.mysql.cj.jdbc.Driver");
		// 	dbconnection = DriverManager.getConnection(DB_URL,USER,PASS);
		// 	dbconnection.setAutoCommit(false);
		// 	activeConnection = true;
		// } catch(ClassNotFoundException ex) {
		// 	System.out.println("Error: unable to load driver class!");
		// 	System.exit(1);
		// } catch (SQLException ex) {
		//     // handle any errors
		//     System.out.println("SQLException: " + ex.getMessage());
		//     System.out.println("SQLState: " + ex.getSQLState());
		//     System.out.println("VendorError: " + ex.getErrorCode());
		// }
	// }

    // public void deactivateConnection( TXN_STATUS txn_status )
	// {
		// Okay to keep deactivating an already deactivated connection
		// activeConnection = false;
		// if( dbconnection != null ){
		// 	try{
		// 		if( txn_status == TXN_STATUS.COMMIT )
		// 			dbconnection.commit();
		// 		else
		// 			dbconnection.rollback();

		// 		dbconnection.close();
		// 		dbconnection = null;

		// 		// Nullify all DAO objects
		// 		studentDAO = null;
		// 	}
		// 	catch (SQLException ex) {
		// 	    // handle any errors
		// 	    System.out.println("SQLException: " + ex.getMessage());
		// 	    System.out.println("SQLState: " + ex.getSQLState());
		// 	    System.out.println("VendorError: " + ex.getErrorCode());
		// 	}
		// }
	// }

    public AdminDAO getAdminDAO() {
        this.adminDAO = new AdminDAO(dataSource, platformTransactionManager, jdbcTemplate);
        return this.adminDAO;
    }

    public CartItemDAO getCartItemDAO() {
        this.cartItemDAO = new CartItemDAO(dataSource, platformTransactionManager, jdbcTemplate);
        return this.cartItemDAO;
    }

    public CustomerDAO getCustomerDAO() {
        this.customerDAO = new CustomerDAO(dataSource, platformTransactionManager, jdbcTemplate);
        return this.customerDAO;
    }

    public ProductDAO getProductDAO() {
        this.productDAO = new ProductDAO(dataSource, platformTransactionManager, jdbcTemplate);
        return this.productDAO;
    }

    public ReviewDAO getReviewDAO() {
        this.reviewDAO = new ReviewDAO(dataSource, platformTransactionManager, jdbcTemplate);
        return this.reviewDAO;
    }

    public SellerDAO getSellerDAO() {
        this.sellerDAO = new SellerDAO(dataSource, platformTransactionManager, jdbcTemplate);
        return this.sellerDAO;
    }

    public TransactionDAO getTransactionDAO() {
        this.transactionDAO = new TransactionDAO(dataSource, platformTransactionManager, jdbcTemplate);
        return this.transactionDAO;
    }

    public WalletDAO getWalletDAO() {
        this.walletDAO = new WalletDAO(dataSource, platformTransactionManager, jdbcTemplate);
        return this.walletDAO;
    }
}
