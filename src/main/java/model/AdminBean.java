package model;

import annotations.sql.SqlTable;
import model.Abstract.AbstractUser;

@SqlTable(tableName = "consumables_admins")
public class AdminBean extends AbstractUser {
}