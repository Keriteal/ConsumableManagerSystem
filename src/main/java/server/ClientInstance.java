package server;

import model.protobuf.Login;

public class ClientInstance {
    private int id = 0;
    private Login.LoginRequest.UserType userType = Login.LoginRequest.UserType.USER;
    private String secret = "";

    public ClientInstance(int id, Login.LoginRequest.UserType userType, String secret) {
        setId(id);
        setUserType(userType);
        setSecret(secret);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Login.LoginRequest.UserType getUserType() {
        return userType;
    }

    public void setUserType(Login.LoginRequest.UserType userType) {
        this.userType = userType;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "ClientInstance{" +
                "id=" + id +
                ", userType=" + userType +
                ", secret='" + secret + '\'' +
                '}';
    }
}
