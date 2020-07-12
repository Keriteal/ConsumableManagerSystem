package exceptions;

public class NoSuchUserException extends LoginFailedException {
    String user;
    public NoSuchUserException(String username) {
        this.user = username;
    }

    @Override
    public String getMessage() {
        return "User " + user + " Not Found";
    }
}
