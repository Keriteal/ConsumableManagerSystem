package Model;

import Annotations.SqlColumn;
import Annotations.SqlDateTime;
import Annotations.SqlInteger;
import Annotations.SqlTable;

import java.sql.Time;

@SqlTable(tableName = "consumable_record")
public class RecordBean {
    @SqlColumn(name = "cr_id")
    @SqlInteger
    private int id;

    @SqlColumn(name = "ca_id")
    @SqlInteger
    private int adminUser;

    @SqlColumn(name = "cu_id")
    @SqlInteger
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
