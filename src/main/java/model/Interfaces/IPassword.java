package model.Interfaces;

public interface IPassword {
    String SavedPassword = "";

    boolean Compare(IPassword pass);
    boolean Compare(String string);
}
