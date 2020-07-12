package dao;

import exceptions.LoginFailedException;
import exceptions.NoSuchUserException;
import model.UserBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.HikariCpUtils;
import utils.SqlLanguageUtils;
import utils.SqlStatementUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static final int LOGIN_FAILED = -1;

    private static final Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    private static final Class<UserBean> UserClass = UserBean.class;
    private static final String QueryString = SqlStatementUtils.generateQuery(UserClass);

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

    public UserBean getUserBean(int userId) throws NoSuchUserException {
        UserBean user = new UserBean();
        try (Connection connection = HikariCpUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(QueryString)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("cu_id"));
                user.setName(rs.getString("cu_name"));
                user.setPassword(rs.getString("cu_password"));
                user.setContact(rs.getString("cu_contact"));
                user.setRegisterTime(rs.getTimestamp("cu_register_time"));
                user.setLatestLogin(rs.getTimestamp("cu_latest_login"));
            } else {
                throw new NoSuchUserException();
            }
        } catch (SQLException sqlException) {
            logger.error(sqlException.getStackTrace());
        }
        return user;
    }
}
