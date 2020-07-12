package utils;

import dao.AdminDAO;
import dao.UserDAO;
import exceptions.LoginFailedException;
import model.UserBean;
import model.protobuf.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ClientInstance;

public class AuthenticationUtils {
    private static final Logger logger = LogManager.getLogger();
    private static final AdminDAO admin = new AdminDAO();
    private static final UserDAO user = new UserDAO();

    public static ClientInstance login(Login.LoginRequest request) throws LoginFailedException {
        //创建实例
        ClientInstance instance = new ClientInstance();
        //获取请求的用户类型
        if (request.getUserType() == Login.LoginRequest.UserType.USER) {
            instance.setUserType(ClientInstance.UserType.NORMAL_USER);
        } else if (request.getUserType() == Login.LoginRequest.UserType.ADMIN) {
            instance.setUserType(ClientInstance.UserType.ADMIN_USER);
        }
        //查找数据库
        switch (instance.getUserType()) {
            case ADMIN_USER:
                break;
            case NORMAL_USER:
                UserBean userBean = new UserBean();
                userBean.setId(request.getId());
                userBean.setPassword(request.getPassword());
                logger.debug("Try log in: userid = "+ userBean.getId() + "password=" + userBean.getPassword());
                int result = user.query(userBean, UserBean.CONDITION_LOGIN);
                if (result == UserDAO.LOGIN_FAILED) {
                    throw new LoginFailedException();
                }
                break;
            default:
                throw new LoginFailedException();
        }
        return instance;
    }
}
