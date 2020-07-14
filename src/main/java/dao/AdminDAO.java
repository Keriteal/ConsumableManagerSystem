package dao;

import exceptions.NoSuchUserException;
import model.AdminBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.HikariCpUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private static final Logger logger = LogManager.getLogger();

    private static final String SqlListAll = "SELECT * FROM " + AdminBean.TABLE_NAME;
    private static final String SqlQuery = "SELECT * FROM " + AdminBean.TABLE_NAME + " " +
            "WHERE " + AdminBean.COLUMN_USERNAME + "=?";

    public List<AdminBean> listAll() {
        List<AdminBean> list = new ArrayList<>();
        try (Connection connection = HikariCpUtils.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SqlListAll);
            while (rs.next()) {
                AdminBean bean = new AdminBean();
                bean.setUsername(rs.getString(AdminBean.COLUMN_USERNAME));
                bean.setId(rs.getInt(AdminBean.COLUMN_ID));
                bean.setPassword(null);
                bean.setName(rs.getString(AdminBean.COLUMN_NAME));
                bean.setContact(rs.getString(AdminBean.COLUMN_CONTACT));
                bean.setTimeLogin(rs.getTimestamp(AdminBean.COLUMN_TIME_LOGIN));
                list.add(bean);
            }
        } catch (SQLException e) {
            logger.fatal(e.getMessage());
        }
        return list;
    }

    public AdminBean query(String username)
            throws NoSuchUserException {
        AdminBean bean = new AdminBean();
        try (Connection connection = HikariCpUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlQuery)) {
            logger.debug(SqlQuery);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bean.setUsername(rs.getString(AdminBean.COLUMN_USERNAME));
                bean.setId(rs.getInt(AdminBean.COLUMN_ID));
                bean.setName(rs.getString(AdminBean.COLUMN_NAME));
                bean.setContact(rs.getString(AdminBean.COLUMN_CONTACT));
                bean.setPassword(rs.getString(AdminBean.COLUMN_PASSWORD));
                bean.setTimeLogin(rs.getTimestamp(AdminBean.COLUMN_TIME_LOGIN));
            } else {
                throw new NoSuchUserException(username);
            }
        } catch (SQLException e) {
            logger.fatal(e.getMessage());
        }
        return bean;
    }
}
