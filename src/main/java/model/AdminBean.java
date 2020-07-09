package model;

import annotations.sql.*;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import model.Interfaces.IBean;
import model.Interfaces.IPassword;

import java.util.Date;

@SqlTable(tableName = "consumables_admins")
public class AdminBean implements IBean {
    public static final int CONDITION_ID = 0x001;

    @SqlColumn(value = "ca_id")
    @SqlInteger
    @SqlQueryCondition(value = CONDITION_ID)
    @Protobuf(fieldType = FieldType.UINT32)
    private int id;

    @SqlColumn(value = "ca_name")
    @SqlString
    @Protobuf(fieldType = FieldType.STRING)
    private String name;

    @SqlColumn(value = "ca_password")
    @SqlString
    @Protobuf(fieldType = FieldType.STRING)
    private String password;

    @SqlColumn(value = "ca_contact")
    @SqlString
    @Protobuf(fieldType = FieldType.STRING)
    private String contact;

    @SqlColumn(value = "ca_login_datetime")
    @SqlDateTime
    @Protobuf(fieldType = FieldType.DATE)
    private Date login_datetime;

    @Override
    public int getIdentity() {
        return this.id;
    }
}