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
    @SqlQueryCondition(CONDITION_ID | CONDITION_LOGIN)
    private int id;

    @SqlColumn("cu_name")
    private String name;

    @SqlColumn("cu_contact")
    private String contact;

    @SqlColumn("cu_password")
    @SqlQueryCondition(CONDITION_LOGIN)
    private IPassword password;

    @SqlColumn("cu_register_time")
    private Timestamp registerTime;

    @SqlColumn("cu_latest_login")
    private Timestamp latestLogin;

    @Override
    public int getIdentity() {
        return id;
    }
}
