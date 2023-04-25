ALTER TABLE customer
ADD CONSTRAINT FK_CustomerWallet
FOREIGN KEY (walletId) REFERENCES wallet(id);

ALTER TABLE seller
ADD CONSTRAINT FK_SellerWallet
FOREIGN KEY (walletId) REFERENCES wallet(id);

ALTER TABLE seller_locations
ADD CONSTRAINT FK_Seller_locations_Seller
FOREIGN KEY (sellerId) REFERENCES seller(id) ON DELETE CASCADE;

ALTER TABLE product
ADD CONSTRAINT FK_ProductSeller
FOREIGN KEY (sellerId) REFERENCES seller(id) ON DELETE CASCADE;

ALTER TABLE transaction
ADD CONSTRAINT FK_TransactionCustomer
FOREIGN KEY (customerId) REFERENCES customer(id);

ALTER TABLE transaction
ADD CONSTRAINT FK_TransactionSeller
FOREIGN KEY (sellerId) REFERENCES seller(id);

ALTER TABLE transaction
ADD CONSTRAINT FK_TransactionProduct
FOREIGN KEY (productId) REFERENCES product(id);

ALTER TABLE review
ADD CONSTRAINT FK_ReviewCustomer
FOREIGN KEY (customerId) REFERENCES customer(id);

ALTER TABLE review
ADD CONSTRAINT FK_ReviewProduct
FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE;

ALTER TABLE cart_item
ADD CONSTRAINT FK_Cart_ItemCustomer
FOREIGN KEY (customerId) REFERENCES customer(id) ON DELETE CASCADE;

ALTER TABLE cart_item
ADD CONSTRAINT FK_CartItemProduct
FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE;

-- ALTER TABLE wallet
-- ADD CONSTRAINT FK_WalletCustomer
-- FOREIGN KEY (id) REFERENCES customer(walletId) ON DELETE CASCADE;

-- ALTER TABLE wallet
-- ADD CONSTRAINT FK_WalletSeller
-- FOREIGN KEY (id) REFERENCES seller(walletId) ON DELETE CASCADE;
