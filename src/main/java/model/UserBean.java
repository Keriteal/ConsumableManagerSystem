package model;

import annotations.sql.*;
import model.Interfaces.IBean;
import model.Interfaces.IPassword;
import java.sql.*;

@SqlTable(tableName = "consumables_user", primaryKey = "cu_id")
public class UserBean implements IBean {
    public static final int CONDITION_ID = 0x1;
    public static final int CONDITION_LOGIN = 0x2;

    @SqlColumn("cu_id")
    @SqlQueryCondition(CONDITION_ID)
    private int id;

    @SqlColumn("cu_name")
    @SqlQueryCondition(CONDITION_LOGIN)
    private String name;

    @SqlColumn("cu_contact")
    private String contact;

    @SqlColumn("cu_password")
    @SqlQueryCondition(CONDITION_LOGIN)
    private String password;

    @SqlColumn("cu_register_time")
    private Timestamp registerTime;

    @SqlColumn("cu_latest_login")
    private Timestamp latestLogin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public Timestamp getLatestLogin() {
        return latestLogin;
    }

    public void setLatestLogin(Timestamp latestLogin) {
        this.latestLogin = latestLogin;
    }

    @Override
    public int getIdentity() {
        return id;
    }
}
