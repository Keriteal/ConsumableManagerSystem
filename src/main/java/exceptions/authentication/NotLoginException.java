package exceptions.authentication;

public class NotLoginException extends AuthenticationException{
    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException() {
        super("还没有登录");
    }
}
