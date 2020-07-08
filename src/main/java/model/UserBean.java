package model;

import annotations.sql.*;
import model.Interfaces.IBean;
import model.Interfaces.IPassword;
import java.sql.*;

@SqlTable(tableName = "consumables_user")
public class UserBean implements IBean {
    public static final int CONDITION_ID = 0x001;

    @SqlColumn("cu_id")
    @SqlQueryCondition(value = CONDITION_ID)
    @SqlString
    private int id;

    @SqlColumn("cu_name")
    @SqlString
    private String name;

    @SqlColumn("cu_contact")
    @SqlString
    private String contact;

    @SqlColumn("cu_password")
    @SqlPassword
    private IPassword password;

    @SqlColumn("cu_register_time")
    @SqlDateTime
    private Timestamp registerTime;

    @SqlColumn("cu_latest_login")
    @SqlDateTime
    private Timestamp latestLogin;

    @Override
    public int getIdentity() {
        return id;
    }
}
