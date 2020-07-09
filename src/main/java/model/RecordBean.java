package model;

import annotations.sql.*;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import model.Interfaces.IBean;

import java.sql.Date;
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
    @Protobuf(fieldType = FieldType.UINT32)
    private int id;

    @SqlColumn("ca_id")
    @SqlInteger
    @SqlQueryCondition(CONDITION_UNCONFIRMED)
    @Protobuf(fieldType = FieldType.UINT32)
    private int adminUser;

    @SqlColumn("cu_id")
    @SqlInteger
    @SqlQueryCondition(CONDITION_USER)
    @Protobuf(fieldType = FieldType.UINT32)
    private int applicationUser;

    @SqlColumn("cr_application_datetime")
    @SqlDateTime
    @Protobuf(fieldType = FieldType.DATE)
    private Date applicationTime;

    @SqlColumn("cr_confirmed_datetime")
    @SqlDateTime
    @Protobuf(fieldType = FieldType.DATE)
    private Date confirmedTime;

    @Override
    public int getIdentity() {
        return id;
    }
}
