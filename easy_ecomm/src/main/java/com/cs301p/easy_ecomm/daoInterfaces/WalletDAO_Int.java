package com.cs301p.easy_ecomm.daoInterfaces;

import com.cs301p.easy_ecomm.entityClasses.Wallet;

public interface WalletDAO_Int {
    public Wallet getWalletById(Wallet wallet);

    public int addWallet(Wallet wallet);

    public int deleteWallet(Wallet wallet);

    public int updateWallet(Wallet wallet);
}
