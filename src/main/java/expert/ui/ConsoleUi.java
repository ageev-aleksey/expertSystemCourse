package expert.ui;

import expert.parser.Parser;
import expert.parser.Tokenizer;

import java.util.Scanner;

public class ConsoleUi {

    private Scanner stdin = new Scanner(System.in);

    public void run() {
        printLine("Expert system v1");
        String in = "";
        Parser p = null;
        Tokenizer t = null;
        while (true) {
            in = input();
            if(in.equals("\\q;")) {
                break;
            }
            t = new Tokenizer(in);
            p.parse(t, );
        }
    }
    private String input() {
        print(">> ");
        StringBuilder line = new StringBuilder(stdin.nextLine());
        while(!isEndCommand(line)) {
            print("... ");
            line.append(stdin.nextLine());
        }
        return line.toString();
    }

    private boolean isEndCommand(CharSequence sequence) {
        int i = sequence.length()-1;
        for(; i >= 0 && sequence.charAt(i) == ' '; i-- ) {}
        return sequence.charAt(i) == ';';
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
