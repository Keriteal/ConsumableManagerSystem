package dao;

import exceptions.NoSuchUserException;
import exceptions.register.AlreadyHasUserException;
import model.UserBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.HikariCpUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private static final String QueryGetBean = "SELECT * " +
            "FROM " + UserBean.TABLE_NAME + " " +
            "WHERE " + UserBean.COLUMN_USER_NAME + "=? ";
    private static final String InsertSql = "INSERT INTO " + UserBean.TABLE_NAME + "(" +
            UserBean.COLUMN_USER_NAME + "," + UserBean.COLUMN_PASSWORD + "," +
            UserBean.COLUMN_NAME + "," + UserBean.COLUMN_CONTACT + "," +
            UserBean.COLUMN_TIME_REGISTER + "," + UserBean.COLUMN_TIME_LOGIN +
            ") VALUES (?, ?, ?, ?, NOW(), NOW())";
    private static final String SqlList = "SELECT * FROM " + UserBean.TABLE_NAME;

    public boolean insert(UserBean user) throws AlreadyHasUserException {
        boolean ret = false;
        try (Connection connection = HikariCpUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(InsertSql)) {
            logger.debug(InsertSql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getContact());
            if (ps.executeUpdate() == 1) {
                ret = true;
                logger.debug("注册成功：" + user.getName() + "," + user.getPassword() + "," + user.getContact());
            }
        } catch (SQLException sqlException) {
            logger.fatal("SQLException occurs：" + sqlException.getLocalizedMessage());
            if (sqlException.getLocalizedMessage().contains("Duplicate")) {
                throw new AlreadyHasUserException();
            }
        }
        return ret;
    }

    public UserBean query(String userName) throws NoSuchUserException {
        UserBean user = new UserBean();
        try (Connection connection = HikariCpUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(QueryGetBean)) {
            logger.debug(QueryGetBean);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt(UserBean.COLUMN_ID));
                user.setName(rs.getString(UserBean.COLUMN_NAME));
                user.setPassword(rs.getString(UserBean.COLUMN_PASSWORD));
                user.setContact(rs.getString(UserBean.COLUMN_CONTACT));
                user.setRegisterTime(rs.getTimestamp(UserBean.COLUMN_TIME_REGISTER));
                user.setLatestLogin(rs.getTimestamp(UserBean.COLUMN_TIME_LOGIN));
                connection.createStatement().executeUpdate(
                        "UPDATE " + UserBean.TABLE_NAME + " SET " +
                                UserBean.COLUMN_TIME_LOGIN + "=NOW() " +
                                "WHERE " + UserBean.COLUMN_ID + "=" + user.getId()
                );
            } else {
                throw new NoSuchUserException(userName);
            }
        } catch (SQLException sqlException) {
            logger.error(sqlException.getLocalizedMessage());
        }
        return user;
    }

    public List<UserBean> list() {
        List<UserBean> list = new ArrayList<>();
        try (Connection connection = HikariCpUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SqlList)
        ) {
            while (rs.next()) {
                UserBean bean = new UserBean();
                bean.setId(rs.getInt(UserBean.COLUMN_ID));
                bean.setUsername(rs.getString(UserBean.COLUMN_USER_NAME));
                bean.setName(rs.getString(UserBean.COLUMN_NAME));
                bean.setContact(rs.getString(UserBean.COLUMN_CONTACT));
                bean.setRegisterTime(rs.getTimestamp(UserBean.COLUMN_TIME_REGISTER));
                bean.setLatestLogin(rs.getTimestamp(UserBean.COLUMN_TIME_LOGIN));
                list.add(bean);
            }
        } catch (SQLException e) {
            logger.fatal(e.getLocalizedMessage());
        }
        return list;
    }
}
