package com.cs301p.easy_ecomm.daoClasses;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.cs301p.easy_ecomm.daoInterfaces.AdminDAO_Int;
import com.cs301p.easy_ecomm.entityClasses.Admin;
import com.cs301p.easy_ecomm.mappers.AdminMapper;

public class AdminDAO implements AdminDAO_Int {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager platformTransactionManager;

    public AdminDAO(DataSource dataSource, PlatformTransactionManager platformTransactionManager, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.platformTransactionManager = platformTransactionManager;
        this.jdbcTemplate = jdbcTemplate;
    }


    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return this.platformTransactionManager;
    }

    public void getPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    // Now, write query functions.
    public Admin getAdminById(Admin admin) {
        String sql = "SELECT * FROM admin WHERE id=" + admin.getId();
        List<Admin> admins = this.jdbcTemplate.query(sql, new AdminMapper());

        if(admins.size() == 0){
            return(null);
        }

        return admins.get(0);
    }

    public Admin getAdminByName(Admin admin) {
        String sql = "SELECT * FROM admin WHERE a_name='" + admin.getA_name() + "'";
        List<Admin> admins = this.jdbcTemplate.query(sql, new AdminMapper());

        if(admins.size() == 0){
            return(null);
        }

        return admins.get(0);
    }
}
