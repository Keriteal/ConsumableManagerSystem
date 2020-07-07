package Model;

public interface Password {
    String SavedPassword = "";

    boolean Compare(Password pass);
    boolean Compare(String string);
}
