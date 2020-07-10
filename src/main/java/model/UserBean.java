package model;

import annotations.sql.*;
import model.Interfaces.IBean;
import model.Interfaces.IPassword;
import java.sql.*;

@SqlTable(tableName = "consumables_user", primaryKey = "cu_id")
public class UserBean implements IBean {
    public static final int CONDITION_ID = 0x001;

    @SqlColumn("cu_id")
    @SqlQueryCondition(value = CONDITION_ID)
    private int id;

    @SqlColumn("cu_name")
    private String name;

    @SqlColumn("cu_contact")
    private String contact;

    @SqlColumn("cu_password")
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
