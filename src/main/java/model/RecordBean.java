package model;

import annotations.sql.*;
import model.Interfaces.IBean;

import java.sql.Timestamp;

@SqlTable(tableName = "consumable_record")
@SqlPrimaryKey("cr_id")
public class RecordBean implements IBean {
    public static final int CONDITION_UNCONFIRMED = 0x001;
    public static final int CONDITION_USER = 0x002;
    public static final int CONDITION_ID = 0x004;

    @SqlColumn("cr_id")
    @SqlInteger
    @SqlQueryCondition(CONDITION_ID)
    private int id;

    @SqlColumn("ca_id")
    @SqlInteger
    @SqlQueryCondition(CONDITION_UNCONFIRMED)
    private int adminUser;

    @SqlColumn("cu_id")
    @SqlInteger
    @SqlQueryCondition(CONDITION_USER)
    private int applicationUser;

    @SqlColumn("cr_application_datetime")
    @SqlDateTime
    private Timestamp applicationTime;

    @SqlColumn("cr_confirmed_datetime")
    @SqlDateTime
    private Timestamp confirmedTime;

    @Override
    public int getIdentity() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(int adminUser) {
        this.adminUser = adminUser;
    }

    public int getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(int applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Timestamp getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Timestamp applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Timestamp getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(Timestamp confirmedTime) {
        this.confirmedTime = confirmedTime;
    }
}
