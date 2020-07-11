package utils;

import exceptions.LoginFailedException;
import exceptions.NoSuchUserException;
import exceptions.PasswordWrongException;
import protobufmodels.LoginRequest;
import server.ClientInstance;

public class AuthenticationUtils {
    public static ClientInstance login(LoginRequest.LoginRequset request) throws NoSuchUserException, PasswordWrongException {
        ClientInstance instance = new ClientInstance();
        if(request.getUserType() == LoginRequest.LoginRequset.UserType.USER) {
            instance.setUserType(ClientInstance.UserType.NORMAL_USER);
        } else if (request.getUserType() == LoginRequest.LoginRequset.UserType.ADMIN) {
            instance.setUserType(ClientInstance.UserType.ADMIN_USER);
        }

        return instance;
    }
}
