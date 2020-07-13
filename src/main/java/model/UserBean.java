package model;

import annotations.sql.*;
import model.Interfaces.IBean;
import model.Interfaces.IPassword;
import java.sql.*;

import static model.UserBean.COLUMN_ID;

@SqlTable(tableName = UserBean.TABLE_NAME, primaryKey = COLUMN_ID)
public class UserBean implements IBean {
    public static final int CONDITION_ID = 1;
    public static final int CONDITION_LOGIN = 2;
    public static final int CONDITION_GET_BEAN = 4;

    public static final String TABLE_NAME = "consumables_user";

    public static final String COLUMN_ID = "cu_id";
    public static final String COLUMN_USER_NAME = "cu_user_name";
    public static final String COLUMN_PASSWORD = "cu_password";
    public static final String COLUMN_NAME = "cu_name";
    public static final String COLUMN_CONTACT = "cu_contact";
    public static final String COLUMN_REGISTER_TIME = "cu_register_time";
    public static final String COLUMN_LOGIN_TIME = "cu_latest_login";

    @SqlColumn(COLUMN_ID)
    @SqlQueryCondition(CONDITION_ID)
    private int id;

    @SqlColumn(COLUMN_USER_NAME)
    @SqlQueryCondition(CONDITION_LOGIN)
    private String username;

    @SqlColumn(COLUMN_PASSWORD)
    @SqlQueryCondition(CONDITION_LOGIN)
    private String password;

    @SqlColumn(COLUMN_NAME)
    @SqlQueryCondition(CONDITION_GET_BEAN)
    private String name;

    @SqlColumn(COLUMN_CONTACT)
    private String contact;

    @SqlColumn(COLUMN_REGISTER_TIME)
    private Timestamp registerTime;

    @SqlColumn(COLUMN_LOGIN_TIME)
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int getIdentity() {
        return id;
    }
}
