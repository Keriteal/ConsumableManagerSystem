package model;

import annotations.sql.*;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import model.Interfaces.IBean;

import java.sql.Date;
import java.sql.Timestamp;

@SqlTable(tableName = RecordBean.TABLE_NAME, primaryKey = RecordBean.PRIMARY_KEY)
public class RecordBean implements IBean {
    public static final String PRIMARY_KEY = "cr_id";
    public static final String TABLE_NAME = "consumable_record";
    public static final String COLUMN_ID = "cr_id";
    public static final String COLUMN_ADMIN = "ca_id";
    public static final String COLUMN_USER = "cu_id";
    public static final String COLUMN_ITEM = "ci_id";
    public static final String COLUMN_TIME_COMMIT = "cr_application_datetime";
    public static final String COLUMN_TIME_CONFIRM = "cr_confirmed_datetime";

    public static final int CONDITION_UNCONFIRMED = 0x001;
    public static final int CONDITION_USER = 0x002;
    public static final int CONDITION_ID = 0x004;

    @SqlColumn(value = PRIMARY_KEY, columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_ID)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int id; // 记录id

    @SqlColumn(value = COLUMN_ADMIN, columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_UNCONFIRMED)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int adminUser; // 管理员id（审核者）

    @SqlColumn(value = COLUMN_USER, columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_USER)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int applicationUser; // 申请人id

    @SqlColumn(value = COLUMN_USER, columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_USER)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int consumableItemId; // 耗材id

    @SqlColumn(value = COLUMN_TIME_COMMIT, columnType = ColumnType.DATETIME)
    @Protobuf(fieldType = FieldType.DATE, required = true)
    private Timestamp applicationTime; // 申请时间

    @SqlColumn(value = COLUMN_TIME_CONFIRM, columnType = ColumnType.DATETIME, nullable = true)
    @Protobuf(fieldType = FieldType.DATE)
    private Timestamp confirmedTime; // 审核通过时间

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

    public int getConsumableItemId() {
        return consumableItemId;
    }

    public void setConsumableItemId(int consumableItemId) {
        this.consumableItemId = consumableItemId;
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
