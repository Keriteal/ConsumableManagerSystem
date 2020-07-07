package model.Abstract;

import model.Interfaces.IPassword;

public abstract class AbstractUser {
    int id;
    String username;
    IPassword IPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public IPassword getIPassword() {
        return IPassword;
    }

    public void setIPassword(IPassword IPassword) {
        this.IPassword = IPassword;
    }
}
