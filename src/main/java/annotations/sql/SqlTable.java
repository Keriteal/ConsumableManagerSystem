package annotations.sql;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlTable {
    String tableName();
    String primaryKey();
}
