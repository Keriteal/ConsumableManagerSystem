package utils;

import annotations.sql.ColumnType;
import annotations.sql.SqlColumn;
import annotations.sql.SqlQueryCondition;
import annotations.sql.SqlTable;
import model.Interfaces.IBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

public class SqlStatementUtils {
    // PreparedStatement 生成
    public static final String clazzName = SqlStatementUtils.class.getName();
    private static final Class<SqlTable> sqlTableClass = SqlTable.class;
    private static final Class<SqlColumn> sqlColumnClass = SqlColumn.class;
    private static final Class<SqlQueryCondition> sqlQueryConditionClass = SqlQueryCondition.class;

    private static final Logger logger = LogManager.getLogger();

    public static String generateQuery(Class<? extends IBean> clazz) {
        StringBuilder sb = new StringBuilder("SELECT ");
        if (!clazz.isAnnotationPresent(sqlTableClass)) {
            return null;
        }
        String tableName = clazz.getAnnotation(sqlTableClass).tableName();
        String pkName = clazz.getAnnotation(sqlTableClass).primaryKey();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(sqlColumnClass)) {
                continue;
            }
            sb.append(field.getAnnotation(sqlColumnClass).value()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length()).append(" FROM ").append(tableName)
                .append(" WHERE ").append(pkName).append("=?");
        return sb.toString();
    }

    public static String generateList(Class<? extends IBean> clazz) {
        StringBuilder sb = new StringBuilder("SELECT ");
        if(!clazz.isAnnotationPresent(sqlTableClass)) {
            return null;
        }
        String tableName = clazz.getDeclaredAnnotation(sqlTableClass).tableName();
        String pkName = clazz.getAnnotation(sqlTableClass).primaryKey();
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields) {
            if (!field.isAnnotationPresent(sqlColumnClass)) {
                continue;
            }
            sb.append(field.getAnnotation(sqlColumnClass).value()).append(", ");
        }
        sb.delete(sb.length() - 3, sb.length()).append(" FROM ").append(tableName);
        return sb.toString();
    }

    public static String generateDelete(Class<? extends IBean> clazz) {
        StringBuilder sb = new StringBuilder("DELETE FROM ");
        if (!clazz.isAnnotationPresent(sqlTableClass)) {
            return null;
        }
        String tableName = clazz.getAnnotation(sqlTableClass).tableName();
        String pkName = clazz.getAnnotation(sqlTableClass).primaryKey();

        sb.append(tableName).append(" WHERE ").append(pkName).append("=?");
        return sb.toString();
    }

    public static String generateInsert(Class<? extends IBean> clazz) {
        if (!clazz.isAnnotationPresent(sqlTableClass)) {
            return null;
        }

        StringBuilder sb = new StringBuilder("INSERT INTO ");
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
        if (!clazz.isAnnotationPresent(sqlTableClass)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("UPDATE ");
        String tableName = clazz.getAnnotation(sqlTableClass).tableName();
        String pkName = clazz.getAnnotation(sqlTableClass).primaryKey();

        sb.append(tableName).append(" SET ");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(sqlColumnClass) || field.getAnnotation(sqlColumnClass).value().equals(pkName)) {
                continue;
            }
            sb.append(field.getAnnotation(sqlColumnClass).value()).append("=");
            sb.append("?, ");
        }
        sb.deleteCharAt(sb.length() - 2).append("WHERE ").append(pkName).append("=?");
        return sb.toString();
    }

    public static String generateQueryCondition(Class<? extends IBean> clazz, int condition) {
        if (!clazz.isAnnotationPresent(sqlTableClass)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("SELECT ");
        String tableName = clazz.getAnnotation(sqlTableClass).tableName();
        String pkName = clazz.getAnnotation(sqlTableClass).primaryKey();

        sb.append(pkName).append(" FROM ").append(tableName).append(" WHERE ");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(sqlColumnClass) || !field.isAnnotationPresent(sqlQueryConditionClass)) {
                continue;
            }
            if ((field.getAnnotation(sqlQueryConditionClass).value() & condition) == 0) {
                continue;
            }
            sb.append(field.getAnnotation(sqlColumnClass).value()).append("=");
            sb.append("? AND ");
        }
        sb.delete(sb.length() - 5, sb.length()).append(";");
        return sb.toString();
    }
}
