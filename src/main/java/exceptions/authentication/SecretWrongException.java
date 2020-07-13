package exceptions.authentication;

public class SecretWrongException extends AuthenticationException {
    public SecretWrongException(String message) {
        super(message);
    }

    public SecretWrongException() {
        super("密钥验证失败");
    }
}
