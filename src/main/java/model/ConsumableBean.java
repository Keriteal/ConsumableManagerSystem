package model;

import annotations.sql.*;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import model.Interfaces.IBean;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import java.sql.Timestamp;

@SqlTable(tableName = "consumable_items", primaryKey = "ci_id")
@ProtobufClass
public class ConsumableBean implements IBean {
    public static final int CONDITION_ID = 0x001;

    @SqlColumn(value = "ci_id", columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_ID)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int id; // 物品id

    @SqlColumn(value = "ci_name",columnType = ColumnType.STRING)
    @Protobuf(fieldType = FieldType.STRING, required = true)
    private String name; // 姓名

    @SqlColumn(value = "ci_stock",columnType = ColumnType.UINT)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int stock; // 剩余库存

    @SqlColumn(value = "ci_added_datetime",columnType = ColumnType.DATETIME)
    @Protobuf(fieldType = FieldType.DATE, required = true)
    private Timestamp addedTime; // 添加时间

    @SqlColumn(value = "ci_modified_datetime", columnType = ColumnType.DATETIME)
    @Protobuf(fieldType = FieldType.DATE, required = true)
    private Timestamp modifiedTime; // 修改时间

    @Override
    public int getIdentity() {
        return id;
    }
}
