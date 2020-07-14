package model;

import annotations.sql.*;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import model.Interfaces.IBean;

import java.sql.Date;
import java.sql.Timestamp;

@SqlTable(tableName = RecordBean.TABLE_NAME, primaryKey = RecordBean.COLUMN_ID)
public class RecordBean implements IBean {
    public static final String PRIMARY_KEY = "cr_id";

    public static final String TABLE_NAME = "consumables_record";

    public static final String COLUMN_ID = "cr_id";
    public static final String COLUMN_USER = "cu_id";
    public static final String COLUMN_ITEM = "ci_id";
    public static final String COLUMN_ADMIN = "ca_id";
    public static final String COLUMN_COUNT = "cr_count";
    public static final String COLUMN_TIME_COMMIT = "cr_time_commit";
    public static final String COLUMN_TIME_CONFIRM = "cr_time_confirm";

    public static final int CONDITION_UNCONFIRMED = 0x001;
    public static final int CONDITION_USER = 0x002;
    public static final int CONDITION_ID = 0x004;

    @SqlColumn(value = PRIMARY_KEY, columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_ID)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int id; // 记录id


    @SqlColumn(value = COLUMN_USER, columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_USER)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int userId; // 申请人id

    @SqlColumn(value = COLUMN_USER, columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_USER)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int itemId; // 耗材id

    @SqlColumn(value = COLUMN_COUNT, columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_USER)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int count; // 数量

    @SqlColumn(value = COLUMN_TIME_COMMIT, columnType = ColumnType.DATETIME)
    @Protobuf(fieldType = FieldType.DATE, required = true)
    private Timestamp commitTime; // 申请时间

    @SqlColumn(value = COLUMN_ADMIN, columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_UNCONFIRMED)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int adminId; // 管理员id（审核者）

    @SqlColumn(value = COLUMN_TIME_CONFIRM, columnType = ColumnType.DATETIME, nullable = true)
    @Protobuf(fieldType = FieldType.DATE)
    private Timestamp confirmTime; // 审核通过时间

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Timestamp getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Timestamp commitTime) {
        this.commitTime = commitTime;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Timestamp getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Timestamp confirmTime) {
        this.confirmTime = confirmTime;
    }
}
