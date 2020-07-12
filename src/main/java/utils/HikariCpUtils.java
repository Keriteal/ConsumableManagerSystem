package utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCpUtils {
    public static HikariDataSource dataSource = null;

    public static DataSource getDataSource(){
        try {
            if(dataSource == null) {
                InputStream is = HikariDataSource.class.getClassLoader().getResourceAsStream("hikaricp.properties");
                Properties pros = new Properties();
                pros.load(is);
                HikariConfig config = new HikariConfig(pros);
                dataSource = new HikariDataSource(config);
            }
            return dataSource;
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}
