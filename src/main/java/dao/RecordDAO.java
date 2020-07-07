package dao;

import model.ConsumableBean;
import model.RecordBean;
import model.UserBean;
import utils.HikariCpUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RecordDAO {
    public int apply(ConsumableBean consumable, UserBean user) {
        return 0;
    }

    public RecordBean listUnconfirmed() {
        try{
            Connection connection = HikariCpUtils.getConnection();
            Statement stmt = null;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
