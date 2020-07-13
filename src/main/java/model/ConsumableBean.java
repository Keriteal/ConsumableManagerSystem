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

    public static final String TABLE_NAME = "consumable_items";

    public static final String COLUMN_ID = "ci_id";
    public static final String COLUMN_NAME = "ci_name";
    public static final String COLUMN_STOCK = "ci_stock";
    public static final String COLUMN_TIME_ADD = "ci_added_datetime";
    public static final String COLUMN_TIME_MODIFIED = "ci_modified_datetime";

    @SqlColumn(value = COLUMN_ID, columnType = ColumnType.UINT)
    @SqlQueryCondition(CONDITION_ID)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int id; // 物品id

    @SqlColumn(value = COLUMN_NAME, columnType = ColumnType.STRING)
    @Protobuf(fieldType = FieldType.STRING, required = true)
    private String name; // 名字

    @SqlColumn(value = COLUMN_STOCK, columnType = ColumnType.UINT)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int stock; // 剩余库存

    @SqlColumn(value = COLUMN_TIME_ADD, columnType = ColumnType.DATETIME)
    @Protobuf(fieldType = FieldType.DATE, required = true)
    private Timestamp addedTime; // 添加时间

    @SqlColumn(value = COLUMN_TIME_MODIFIED, columnType = ColumnType.DATETIME)
    @Protobuf(fieldType = FieldType.DATE, required = true)
    private Timestamp modifiedTime; // 修改时间

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Timestamp getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Timestamp addedTime) {
        this.addedTime = addedTime;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
