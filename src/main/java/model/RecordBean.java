package model;

import annotations.sql.*;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import model.Interfaces.IBean;

import java.sql.Date;

@SqlTable(tableName = "consumable_record", primaryKey = RecordBean.PRIMARY_KEY)
public class RecordBean implements IBean {
    public static final String PRIMARY_KEY = "cr_id";

    public static final int CONDITION_UNCONFIRMED = 0x001;
    public static final int CONDITION_USER = 0x002;
    public static final int CONDITION_ID = 0x004;

    @SqlColumn(value = PRIMARY_KEY, columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_ID)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int id; // 记录id

    @SqlColumn(value = "ca_id", columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_UNCONFIRMED)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int adminUser; // 管理员id（审核者）

    @SqlColumn(value = "cu_id", columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_USER)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int applicationUser; // 申请人id

    @SqlColumn(value = "cr_application_datetime", columnType = ColumnType.DATETIME)
    @Protobuf(fieldType = FieldType.DATE, required = true)
    private Date applicationTime; // 申请时间

    @SqlColumn(value = "cr_confirmed_datetime", columnType = ColumnType.DATETIME, nullable = true)
    @Protobuf(fieldType = FieldType.DATE)
    private Date confirmedTime; // 审核通过时间

    @Override
    public int getIdentity() {
        return id;
    }
}
