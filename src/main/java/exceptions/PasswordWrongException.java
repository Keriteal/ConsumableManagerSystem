package exceptions;

public class PasswordWrongException extends LoginFailedException{
    String username;
    public PasswordWrongException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "Password of user " + username + " was wrong";
    }
}
