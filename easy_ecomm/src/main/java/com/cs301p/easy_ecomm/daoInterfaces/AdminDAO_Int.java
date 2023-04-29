package com.cs301p.easy_ecomm.daoInterfaces;

import com.cs301p.easy_ecomm.entityClasses.Admin;

public interface AdminDAO_Int {
    public Admin getAdminById(Admin admin);
    public Admin getAdminByName(Admin admin);
}
