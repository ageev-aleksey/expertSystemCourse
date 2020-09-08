package expert.ui;

import expert.parser.Parser;
import expert.parser.Tokenizer;

import java.util.Scanner;

public class ConsoleUi implements Ui {

    private Scanner stdin = new Scanner(System.in);
    private StringBuilder line = new StringBuilder();

    @Override
    public StringBuilder getCommand() {
        line.setLength(0);
        print(">>> ");
        line.append(stdin.nextLine());
        return line;
    }

    @Override
    public StringBuilder tryGetCommand() {
        print("... ");
        line.append(stdin.nextLine());
        return line;
    }

    @Override
    public void displayMessage(String message) {
        printLine(message);
    }




    private void printLine(String s) {
        System.out.println(s);
        System.out.flush();
    }

    private void print(String s) {
        System.out.print(s);
        System.out.flush();
    }

}
