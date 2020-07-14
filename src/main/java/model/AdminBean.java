package model;

import annotations.sql.*;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import model.Interfaces.IBean;

import java.sql.Timestamp;
import java.util.Date;

@SqlTable(tableName = AdminBean.TABLE_NAME, primaryKey = AdminBean.COLUMN_ID)
public class AdminBean implements IBean {
    public static final int CONDITION_ID = 0x001;
    public static final int CONDITION_LOGIN = 4;

    public static final String TABLE_NAME = "consumables_admin";

    public static final String COLUMN_ID = "ca_id";
    public static final String COLUMN_USERNAME = "ca_username";
    public static final String COLUMN_PASSWORD = "ca_password";
    public static final String COLUMN_NAME = "ca_name";
    public static final String COLUMN_CONTACT = "ca_contact";
    public static final String COLUMN_TIME_LOGIN = "ca_time_login";

    @SqlColumn(value = COLUMN_ID, columnType = ColumnType.INT)
    @SqlQueryCondition(value = CONDITION_ID)
    private int id; // 管理员id

    @SqlColumn(value = COLUMN_USERNAME, columnType = ColumnType.STRING)
    private String username; // 管理员密码

    @SqlColumn(value = COLUMN_PASSWORD, columnType = ColumnType.STRING)
    private String password; // 管理员密码

    @SqlColumn(value = COLUMN_NAME, columnType = ColumnType.STRING)
    private String name; // 管理员姓名

    @SqlColumn(value = COLUMN_CONTACT, columnType = ColumnType.STRING)
    private String contact; // 管理员联系方式

    @SqlColumn(value = COLUMN_TIME_LOGIN, columnType = ColumnType.DATETIME)
    private Timestamp timeLogin; //最近登录时间

    @Override
    public int getIdentity() {
        return this.id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Timestamp getTimeLogin() {
        return timeLogin;
    }

    public void setTimeLogin(Timestamp timeLogin) {
        this.timeLogin = timeLogin;
    }
}