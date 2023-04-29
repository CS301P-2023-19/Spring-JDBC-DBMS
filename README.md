# EasyEcomm

- This project was made as part of the course: Database Systems Management - Lab (CS301P), at IIIT-Bangalore.

## Requirenments and Running the Project

- The project is setup as a maven project and uses spring-boot.
- This project makes use of the spring-jdbc framework to work with a MySQL database and demonstrates several usecases pertaining to an ecommerce application.
- The dependencies can be installed using the pom.xml file in the /easy_ecomm directory.

- You can install maven by running:

```bash
sudo apt install maven
```

- Setting up the application.properties.example and beans.xml.example files in the <project_root>/src/main/resources directory:
    - In the application.properties file, replace "root" and "root" with your MySQL username and password for the spring.datasource.username and spring.datasource.password fields.
    - In the beans.xml file, for the bean with id = "dataSource", replace "root" and "root" with your MySQL username and password for properties username and password.
    - Rename both files and remove the trailing ".example" from their names.

- If you do not have maven installed, you can run the project using an IDE such as VScode or IntelliJ.
- You should have a MySQL server running on localhost, port 3306.

### In your MySQL prompt, run the following to set up a database.

~~~~sql
CREATE DATABASE easyEcommApp;
USE DATABASE easyEcommApp;
SOURCE <directory_where_the_project_is_cloned>/easy_ecomm/SQL/create_ecomm.sql;
SOURCE <directory_where_the_project_is_cloned>/easy_ecomm/SQL/alter_ecomm.sql;
~~~~

- If you wish to add dummy data for customer table and seller table, run the following SQL statement:

~~~~sql
SOURCE <directory_where_the_project_is_cloned>/easy_ecomm/SQL/insert_ecomm.sql;
~~~~

- The above statement will also setup an admin login for the project with a_name: 'DB_ADMIN' and a_password: 'ADMIN_PASS'

- cd to the project root and run the project as follows:

```bash
cd easy_ecomm
mvn spring-boot:run
```

## Features
- The application provides a user choice menu on startup where the user can choose to login as one of:
    - Admin
    - Customer
    - Seller
- Once the user makes a valid choice, they are asked to login using their credentials:
    - Email and Password for customers and sellers.
    - Name and Password for admin.
- On successful login, a menu is displayed as per the type of user which has logged in 

### Admin
- Add a new customer to platform.
- Add a new seller to platform.
- Remove a customer from platform.
- Remove a seller from platform.
- List all customers.
- List all sellers.

### Customer
- List products with filter and/or sort.
- Add a product to cart.
- Remove a product from cart.
- Update product in cart.
- List products in cart.
- Purchase products in cart.
- Review a product (given that they have purchased it).
- Return a product (within 7 days of purchase).
- Link a wallet.
- Update wallet.
- View reviews that they have written.
- View reviews for a particular product.

### Seller
- Add a product to platform.
- Remove a product from platform.
- Update a product on platform.
- Link a wallet.
- Update a wallet.
- List products which are offered by them.

## Usage

- Run the project as follows:
```bash
cd easy_ecomm
mvn spring-boot:run
```
