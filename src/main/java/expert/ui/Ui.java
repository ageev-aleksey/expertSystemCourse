package expert.ui;

public interface Ui {
    StringBuilder getCommand();
    StringBuilder tryGetCommand();
    void displayMessage(String message);
}
