package server;

public class ClientInstance {
    public enum UserType{
        NORMAL_USER, ADMIN_USER
    }

    private int id = 0;
    private UserType userType = UserType.NORMAL_USER;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
