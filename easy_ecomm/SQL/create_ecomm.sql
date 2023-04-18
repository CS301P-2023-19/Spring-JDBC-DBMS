CREATE TABLE admin(
    id INT NOT NULL AUTO_INCREMENT,
    a_name VARCHAR(255) NOT NULL UNIQUE,
    a_password VARCHAR(255) NOT NULL,
    CONSTRAINT admin_PK PRIMARY KEY (id)
);

CREATE TABLE customer(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL, 
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    walletId INT UNIQUE DEFAULT null,
    CONSTRAINT customerPK PRIMARY KEY (id)
);

CREATE TABLE seller(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL, 
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    walletId INT UNIQUE DEFAULT null, 
    CONSTRAINT sellerPK PRIMARY KEY (id)
);

CREATE TABLE seller_locations(
    sellerId INT NOT NULL,
    location VARCHAR(100) NOT NULL
);

CREATE TABLE wallet(
    id INT NOT NULL AUTO_INCREMENT,
    credit_card_no VARCHAR(20) NOT NULL UNIQUE,
    money DECIMAL(10,2) NOT NULL,
    CONSTRAINT walletPK PRIMARY KEY (id)
);

CREATE TABLE product(
    id INT NOT NULL AUTO_INCREMENT,
    type VARCHAR(50) DEFAULT 'miscellaneous',
    name VARCHAR(100) NOT NULL,
    sellerId INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantityAvailable INT NOT NULL,
    CONSTRAINT productPK PRIMARY KEY (id)
);

CREATE TABLE transaction(
    id INT NOT NULL,
    customerId INT NOT NULL,
    sellerId INT NOT NULL,
    productId INT NOT NULL,
    date DATE NOT NULL,
    returnStatus BOOLEAN NOT NULL
);

CREATE TABLE review(
    id INT NOT NULL AUTO_INCREMENT,
    customerId INT NOT NULL,
    productId INT NOT NULL,
    stars INT NOT NULL,
    content VARCHAR(500),
    CONSTRAINT reviewPK PRIMARY KEY(id)
);

CREATE TABLE cart_item(
    customerId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL
);
