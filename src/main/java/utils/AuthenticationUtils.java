package utils;

import dao.AdminDAO;
import dao.UserDAO;
import exceptions.LoginFailedException;
import model.UserBean;
import model.protobuf.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ClientInstance;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AuthenticationUtils {
    private static final Logger logger = LogManager.getLogger();
    private static final AdminDAO admin = new AdminDAO();
    private static final UserDAO user = new UserDAO();

    /*
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
                logger.debug("Try log in: userid = " + userBean.getId() + "password=" + userBean.getPassword());
                int result = user.query(userBean, UserBean.CONDITION_LOGIN);
                if (result == UserDAO.LOGIN_FAILED) {
                    throw new LoginFailedException();
                }
                break;
            default:
                throw new LoginFailedException();
        }
        return instance;
    }*/

    public static String generateSecret(int keyLen) throws NoSuchAlgorithmException {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(keyLen);
            SecretKey secretKey = keyGen.generateKey();
            byte[] encoded = secretKey.getEncoded();
            return DatatypeConverter.printHexBinary(encoded).toLowerCase();
    }

    public static boolean IsValidUsername(String username) {
        //TODO 验证用户名合法性
        return true;
    }

    public static boolean IsValidPassword(String password) {
        // TODO: 验证密码可用性
        return true;
    }
}
