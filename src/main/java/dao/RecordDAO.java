package dao;

import model.ConsumableBean;
import model.RecordBean;
import model.UserBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.HikariCpUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RecordDAO {
    private static final String SqlCommit = "INSERT INTO " + RecordBean.TABLE_NAME + "(" +
            RecordBean.COLUMN_USER + "," + RecordBean.COLUMN_ITEM + "," +
            RecordBean.COLUMN_TIME_COMMIT + ") " +
            "VALUES (?,?,NOW());";
    private static final String SqlConfirm = "UPDATE " + RecordBean.TABLE_NAME + " " +
            "SET " + RecordBean.COLUMN_ADMIN + "=?, " +
            RecordBean.COLUMN_TIME_CONFIRM + "=NOW() " +
            "WHERE " + RecordBean.COLUMN_ID + "=?";

    private static final Logger logger = LogManager.getLogger();

    public int apply(ConsumableBean consumable, UserBean user) {
        return 0;
    }

    public RecordBean listUnconfirmed() {
        try {
            Connection connection = HikariCpUtils.getConnection();
            Statement stmt = null;

        } catch (SQLException exception) {
            logger.fatal(exception.getMessage());
        }
        return null;
    }

    public boolean commit(RecordBean record) {
        try (Connection connection = HikariCpUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlCommit)) {
            logger.debug(SqlCommit);
            ps.setInt(1, record.getApplicationUser());
            ps.setInt(2, record.getConsumableItemId());
            int rows = ps.executeUpdate();
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.fatal(e.getMessage());
        }
        return false;
    }

    public boolean confirm(RecordBean record) {
        try (Connection connection = HikariCpUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlConfirm)) {
            logger.debug(SqlConfirm);
            ps.setInt(1, record.getId());
            ps.setInt(2, record.getAdminUser());
            int rows = ps.executeUpdate();
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.fatal(e.getMessage());
        }
        return false;
    }
}
