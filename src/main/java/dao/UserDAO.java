package dao;

import annotations.sql.SqlTable;
import exceptions.LoginFailedException;
import exceptions.NoSuchUserException;
import exceptions.register.AlreadyHasUserException;
import model.UserBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.HikariCpUtils;
import utils.SqlStatementUtils;

import java.sql.*;

public class UserDAO {
    public static final int LOGIN_FAILED = -1;

    private static final Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private static final Class<UserBean> UserClass = UserBean.class;
    private static final String QueryString = SqlStatementUtils.generateQuery(UserClass);
    private static final String QueryGetBean = "SELECT * " +
            "FROM " + UserBean.TABLE_NAME + " " +
            "WHERE " + UserBean.COLUMN_USER_NAME + "=? ";
    private static final String InsertSql = "INSERT INTO " + UserClass.getAnnotation(SqlTable.class).tableName() + "(" +
            UserBean.COLUMN_USER_NAME + "," + UserBean.COLUMN_PASSWORD + "," +
            UserBean.COLUMN_NAME + "," + UserBean.COLUMN_CONTACT + "," +
            UserBean.COLUMN_TIME_REGISTER + "," + UserBean.COLUMN_TIME_LOGIN +
            ") VALUES (?, ?, ?, ?, NOW(), NOW())";

    /*
     * @Author keriteal
     * @Description
     * @Date 18:24 2020/7/11
     * @Param [user]
     * @return int 用户id
     **/
    public int query(UserBean user, int QueryCondition) throws LoginFailedException {
        logger.debug("Request Query " + user.getIdentity());
        Connection connection = null;
        PreparedStatement ps = null;
        int ret = LOGIN_FAILED;
        try {
            connection = HikariCpUtils.getConnection();
            ps = connection.prepareStatement(SqlStatementUtils.generateQueryCondition(UserClass, QueryCondition));
            ps.setInt(1, user.getId());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (ret == LOGIN_FAILED) {
                    ret = rs.getInt("cu_id");
                } else {
                    ret = LOGIN_FAILED;
                }
            }
            rs.close();
        } catch (Exception e) {
            logger.error("Exception when query user");
            throw new LoginFailedException();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }
        return ret;
    }

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
            if (sqlException.getLocalizedMessage().contains("for key 'consumables_user.index_name'")) {
                throw new AlreadyHasUserException();
            }
        }
        return ret;
    }

    public UserBean getUserBean(String userName) throws NoSuchUserException {
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
}
