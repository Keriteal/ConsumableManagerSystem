package Utils;

import Annotations.SqlColumn;
import Annotations.SqlTable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SqlLanguageUtils {
    public static String generateQuery(Object bean) {
        StringBuilder sb = new StringBuilder();
        Class clazz = bean.getClass();
        boolean isExist = clazz.isAnnotationPresent(SqlTable.class);

        if(!isExist) {
            return null;
        }
        SqlTable table = (SqlTable)clazz.getAnnotation(SqlTable.class);
        String tableName = table.tableName();
        sb.append("SELECT ");
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            if(!field.isAnnotationPresent(SqlColumn.class)){
                continue;
            }
            SqlColumn column = field.getAnnotation(SqlColumn.class);
            String columnname = column.name();
            String fieldName = field.getName();
            String methodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);

            Object fieldValue =
        }
    }
}
