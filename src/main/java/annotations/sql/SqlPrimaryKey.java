package annotations.sql;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface SqlPrimaryKey {
    public String value();
}
