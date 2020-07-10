package model;

import annotations.sql.*;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import model.Interfaces.IBean;

import java.util.Date;

@SqlTable(tableName = "consumables_admins", primaryKey = "ca_id")
public class AdminBean implements IBean {
    public static final int CONDITION_ID = 0x001;

    @SqlColumn(value = "ca_id", columnType = ColumnType.INT)
    @SqlQueryCondition(value = CONDITION_ID)
    @Protobuf(fieldType = FieldType.UINT32, required = true)
    private int id; // 管理员id

    @SqlColumn(value = "ca_name", columnType = ColumnType.STRING)
    @Protobuf(fieldType = FieldType.STRING, required = true)
    private String name; // 管理员姓名

    @SqlColumn(value = "ca_password", columnType = ColumnType.STRING)
    @Protobuf(fieldType = FieldType.STRING, required = true)
    private String password; // 管理员密码

    @SqlColumn(value = "ca_contact", columnType = ColumnType.STRING)
    @Protobuf(fieldType = FieldType.STRING, required = true)
    private String contact; // 管理员联系方式

    @SqlColumn(value = "ca_login_datetime", columnType = ColumnType.DATETIME)
    @Protobuf(fieldType = FieldType.DATE, required = true)
    private Date login_datetime; //最近登录时间

    @Override
    public int getIdentity() {
        return this.id;
    }
}