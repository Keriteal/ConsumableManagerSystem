package DAO;

import Model.ConsumableBean;
import Model.RecordBean;
import Model.UserBean;
import Utils.HikariCpUtils;
import com.zaxxer.hikari.HikariConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RecordDAO {
    public int apply(ConsumableBean consumable, UserBean user) {

    }

    public RecordBean listUnconfirmed() {
        try{
            Connection connection = HikariCpUtils.getConnection();
            Statement stmt = null;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
