package dao;

import model.ConsumableBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.HikariCpUtils;
import utils.SqlLanguageUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsumableDAO {
    private static final Logger logger = LogManager.getLogger();

    private static final String SqlList = "SELECT * FROM " + ConsumableBean.TABLE_NAME;
    private static final String SqlAdd = "INSERT INTO " + ConsumableBean.TABLE_NAME + "(" +
            ConsumableBean.COLUMN_NAME + ", " + ConsumableBean.COLUMN_STOCK + ", " +
            ConsumableBean.COLUMN_TIME_ADD + ", " + ConsumableBean.COLUMN_TIME_MODIFIED + " ) " +
            "VALUES (?,?,NOW(),NOW())";
    private static final String SqlEdit = "UPDATE " + ConsumableBean.TABLE_NAME + " " +
            "SET " + ConsumableBean.COLUMN_NAME + "=?, " + ConsumableBean.COLUMN_STOCK + "=?, " +
            ConsumableBean.COLUMN_TIME_MODIFIED + "=NOW() " +
            "WHERE " + ConsumableBean.COLUMN_ID + "=?";


    public ArrayList<ConsumableBean> listAll() {
        ArrayList<ConsumableBean> list = new ArrayList<>();
        try (Connection connection = HikariCpUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlList)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ConsumableBean bean = new ConsumableBean();
                bean.setId(rs.getInt(ConsumableBean.COLUMN_ID));
                bean.setName(rs.getString(ConsumableBean.COLUMN_NAME));
                bean.setStock(rs.getInt(ConsumableBean.COLUMN_STOCK));
                bean.setAddedTime(rs.getTimestamp(ConsumableBean.COLUMN_TIME_ADD));
                bean.setModifiedTime(rs.getTimestamp(ConsumableBean.COLUMN_TIME_MODIFIED));
                list.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(ConsumableBean consumable) {
        try (Connection connection = HikariCpUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlAdd)) {
            logger.debug(SqlAdd);
            ps.setString(1, consumable.getName());
            ps.setInt(2, consumable.getStock());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        }
        return false;
    }

    public boolean edit(ConsumableBean consumable) {
        try (Connection connection = HikariCpUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlEdit)) {
            logger.debug(SqlEdit);
            ps.setString(1, consumable.getName());
            ps.setInt(2, consumable.getStock());
            ps.setInt(3, consumable.getId());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        }
        return false;
    }
}
