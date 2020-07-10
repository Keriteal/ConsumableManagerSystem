package annotations.sql;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlColumn {
    String value();
    ColumnType columnType() default ColumnType.INT;
    boolean nullable() default false;
    String description() default "";
}
