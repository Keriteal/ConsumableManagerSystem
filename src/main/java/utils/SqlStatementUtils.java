package utils;

import annotations.sql.ColumnType;
import annotations.sql.SqlColumn;
import annotations.sql.SqlTable;
import model.Interfaces.IBean;

import java.lang.reflect.Field;

public class SqlStatementUtils {
    private static final Class<SqlTable> sqlTableClass = SqlTable.class;
    private static final Class<SqlColumn> sqlColumnClass = SqlColumn.class;

    public static String generateQuery(Class<? extends IBean> clazz) {
        StringBuilder sb = new StringBuilder("SELECT ");
        if (!clazz.isAnnotationPresent(SqlTable.class)) {
            return null;
        }
        String tableName = clazz.getAnnotation(SqlTable.class).tableName();
        String pkName = clazz.getAnnotation(SqlTable.class).primaryKey();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(SqlColumn.class)) {
                continue;
            }
            sb.append(field.getAnnotation(SqlColumn.class).value()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1).append(" FROM ").append(tableName)
                .append(" WHERE ").append(pkName).append("=?");
        return sb.toString();
    }

    public static String generateDelete(Class<? extends IBean> clazz) {
        StringBuilder sb = new StringBuilder("DELETE FROM ");
        if (!clazz.isAnnotationPresent(SqlTable.class)) {
            return null;
        }
        String tableName = clazz.getAnnotation(SqlTable.class).tableName();
        String pkName = clazz.getAnnotation(SqlTable.class).primaryKey();

        sb.append(tableName).append(" WHERE ").append(pkName).append("=?");
        return sb.toString();
    }

    public static String generateInsert(Class<? extends IBean> clazz) {
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        if (!clazz.isAnnotationPresent(sqlTableClass)) {
            return null;
        }
        String tableName = clazz.getAnnotation(sqlTableClass).tableName();
        String pkName = clazz.getAnnotation(sqlTableClass).primaryKey();

        sb.append(tableName).append("(");

        Field[] fields = clazz.getDeclaredFields();
        int count = 0;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(sqlColumnClass) || field.getAnnotation(sqlColumnClass).value().equals(pkName)) {
                continue;
            }
            sb.append(field.getAnnotation(sqlColumnClass).value()).append(",");
            count++;
        }
        sb.deleteCharAt(sb.length() - 1).append(") VALUES (");
        while (count > 0) {
            sb.append("?, ");
            count--;
        }
        sb.deleteCharAt(sb.length() - 2).append(")");
        return sb.toString();
    }

    public static String generateUpdate(Class<? extends IBean> clazz) {
        if (!clazz.isAnnotationPresent(SqlTable.class)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("UPDATE ");
        String tableName = clazz.getAnnotation(sqlTableClass).tableName();
        String pkName = clazz.getAnnotation(sqlTableClass).primaryKey();

        sb.append(tableName).append(" SET ");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(sqlColumnClass)) {
                continue;
            }
            sb.append(field.getAnnotation(sqlColumnClass).value()).append("=");
            if (field.getAnnotation(sqlColumnClass).columnType() == ColumnType.UINT ||
                    field.getAnnotation(sqlColumnClass).columnType() == ColumnType.INT) {
                sb.append("?, ");
            } else {
                sb.append("'?', ");
            }
        }
        sb.deleteCharAt(sb.length() - 2).append("WHERE ").append(pkName).append("=?");
        return sb.toString();
    }
}
