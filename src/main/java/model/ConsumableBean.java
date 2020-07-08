package model;

import annotations.sql.*;
import model.Interfaces.IBean;

import java.sql.Timestamp;

@SqlTable(tableName = "consumable_items")
public class ConsumableBean implements IBean {
    public static final int CONDITION_ID = 0x001;

    @SqlColumn("ci_id")
    @SqlInteger
    @SqlQueryCondition(CONDITION_ID)
    private int id;

    @SqlColumn("ci_name")
    @SqlString
    private String name;

    @SqlColumn("ci_stock")
    @SqlInteger
    private int stock;

    @SqlColumn("ci_added_datetime")
    @SqlDateTime
    private Timestamp addedTime;

    @SqlColumn("ci_modified_datetime")
    @SqlDateTime
    private Timestamp modifiedTime;

    @Override
    public int getIdentity() {
        return id;
    }
}
