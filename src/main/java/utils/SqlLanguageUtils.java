package utils;

import annotations.sql.SqlColumn;
import annotations.sql.SqlQueryCondition;
import annotations.sql.SqlTable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SqlLanguageUtils {
    public static String generateQuery(Object bean, int queryCondition) {
        StringBuilder sb = new StringBuilder();
        Class clazz = bean.getClass();
        boolean isExist = clazz.isAnnotationPresent(SqlTable.class);

        if(!isExist) {
            return null;
        }
        SqlTable table = (SqlTable)clazz.getAnnotation(SqlTable.class);
        String tableName = table.tableName();
        sb.append("SELECT * from ").append(tableName).append(" WHERE 1=1 ");
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            if(!field.isAnnotationPresent(SqlColumn.class) || !field.isAnnotationPresent(SqlQueryCondition.class)){
                continue;
            }
            if(field.getAnnotation(SqlQueryCondition.class).value() != queryCondition) {
                continue;
            }
            SqlColumn column = field.getAnnotation(SqlColumn.class);
            String columnname = column.value();
            String fieldName = field.getName();
            String methodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);

            Object fieldValue = null;
            try {
                Method method = clazz.getMethod(methodName);
                fieldValue = method.invoke(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(fieldValue ==null) {
                continue;
            }
            if(fieldValue instanceof String) {
                sb.append(" and `").append(columnname).append("` = '").append(fieldValue).append("'");
            } else if(fieldValue instanceof Integer) {
                sb.append(" and ").append(columnname).append("=").append(fieldValue);
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }


}
