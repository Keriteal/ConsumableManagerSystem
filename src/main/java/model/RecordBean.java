package model;

import annotations.sql.*;

import java.sql.Time;

@SqlTable(tableName = "consumable_record")
public class RecordBean {
    public static final int CONDITION_UNCONFIRMED = 0x001;
    public static final int CONDITION_USER = 0x002;
    public static final int CONDITION_ID = 0x004;

    @SqlColumn(name = "cr_id")
    @SqlInteger
    @SqlQueryCondition(condition = CONDITION_ID)
    private int id;

    @SqlColumn(name = "ca_id")
    @SqlInteger
    @SqlQueryCondition(condition = CONDITION_UNCONFIRMED)
    private int adminUser;

    @SqlColumn(name = "cu_id")
    @SqlInteger
    @SqlQueryCondition(condition = CONDITION_USER)
    private int applicationUser;

    @SqlColumn(name = "cr_application_datetime")
    @SqlDateTime
    private Time applicationTime;

    @SqlColumn(name = "cr_confirmed_datetime")
    @SqlDateTime
    private Time confirmedTime;

    public RecordBean(int id, int adminUser, int applicationUser, Time applicationTime, Time confirmedTime) {
        this.id = id;
        this.adminUser = adminUser;
        this.applicationUser = applicationUser;
        this.applicationTime = applicationTime;
        this.confirmedTime = confirmedTime;
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

    public Time getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Time applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Time getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(Time confirmedTime) {
        this.confirmedTime = confirmedTime;
    }
}
