package Model;

import Annotations.SqlTable;
import Model.Abstract.AbstractUser;

@SqlTable(tableName = "consumables_admins")
public class AdminBean extends AbstractUser {
}