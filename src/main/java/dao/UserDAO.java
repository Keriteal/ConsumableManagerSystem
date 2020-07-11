package dao;

import exceptions.LoginFailedException;
import model.UserBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.HikariCpUtils;
import utils.SqlLanguageUtils;
import utils.SqlStatementUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {
    static Logger logger = LogManager.getLogger();
    private static final String QueryString = SqlStatementUtils.generateQuery(UserBean.class);
    /*
     * @Author keriteal
     * @Description
     * @Date 18:24 2020/7/11
     * @Param [user]
     * @return int 用户id
     **/
    public static int query(UserBean user, int QueryCondition) throws LoginFailedException {
        logger.debug("Request Query "+user.getIdentity());
        Connection connection = null;
        PreparedStatement ps = null;
        int ret = 0;
        try {
            connection = HikariCpUtils.getConnection();
            ps = connection.prepareStatement()
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            throw new LoginFailedException();
        }
        return ret;
    }
}
