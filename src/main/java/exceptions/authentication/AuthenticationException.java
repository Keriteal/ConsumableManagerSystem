package exceptions.authentication;

public class AuthenticationException extends Exception {
    public AuthenticationException() {
        super("登录信息验证失败");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
