package model;

import annotations.sql.*;
import model.Interfaces.IBean;
import model.Interfaces.IPassword;

@SqlTable(tableName = "consumables_admins")
public class AdminBean implements IBean {
    public static final int CONDITION_ID = 0x001;

    @SqlColumn(value = "ca_id")
    @SqlInteger
    @SqlQueryCondition(value = CONDITION_ID)
    private int id;

    @SqlColumn(value = "ca_name")
    @SqlString
    private String name;

    @SqlColumn(value = "ca_password")
    @SqlString
    private IPassword password;

    @SqlColumn(value = "ca_contact")
    @SqlString
    private String contact;

    @SqlColumn(value = "ca_login_datetime")
    @SqlDateTime
    private String login_datetime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public model.Interfaces.IPassword getPassword() {
        return password;
    }

    public void setPassword(model.Interfaces.IPassword password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLogin_datetime() {
        return login_datetime;
    }

    public void setLogin_datetime(String login_datetime) {
        this.login_datetime = login_datetime;
    }
}