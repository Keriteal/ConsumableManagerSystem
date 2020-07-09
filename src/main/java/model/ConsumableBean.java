package model;

import annotations.sql.*;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import model.Interfaces.IBean;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import java.sql.Timestamp;

@SqlTable(tableName = "consumable_items")
@ProtobufClass
public class ConsumableBean implements IBean {
    public static final int CONDITION_ID = 0x001;

    @SqlColumn("ci_id")
    @SqlInteger
    @SqlQueryCondition(CONDITION_ID)
    @Protobuf(fieldType = FieldType.UINT32, order = 1, required = true)
    private int id;

    @SqlColumn("ci_name")
    @SqlString
    @Protobuf(fieldType = FieldType.STRING, order = 2, required = true)
    private String name;

    @SqlColumn("ci_stock")
    @SqlInteger
    @Protobuf(fieldType = FieldType.UINT32, order = 3, required = true)
    private int stock;

    @SqlColumn("ci_added_datetime")
    @SqlDateTime
    @Protobuf(fieldType = FieldType.DATE, order = 4, required = true)
    private Timestamp addedTime;

    @SqlColumn("ci_modified_datetime")
    @SqlDateTime
    @Protobuf(fieldType = FieldType.DATE, order = 5, required = true)
    private Timestamp modifiedTime;

    @Override
    public int getIdentity() {
        return id;
    }
}
