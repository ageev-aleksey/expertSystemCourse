package expert;

import expert.parser.Parser;
import expert.parser.StepExecutor;
import expert.parser.Tokenizer;
import expert.productional.Dictionary;
import expert.productional.KnowledgeBase;
import expert.productional.impl.BruteForceSolver;
import expert.productional.impl.InMemoryStorage;
import expert.ui.ConsoleUi;
import expert.ui.Ui;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Executor implements StepExecutor {

    @Override
    public void createRule(Iterable<String> conclusions, String premise) {
        StringBuilder b = new StringBuilder();
        int i = 0;
        for(String c : conclusions) {
            i++;
            b.append(c);
            b.append(", ");
        }
        b.append(" > ");
        b.append(premise);
        System.out.println(b);
    }

    @Override
    public void createTerm(String name, String description) {
       System.out.println(name + "-" + description);
    }

    @Override
    public void terms() {
        System.out.println("!!TERMS!!");
    }

    @Override
    public void rules() {
        System.out.println("!!RULES!!");
    }

    @Override
    public void solve(Iterable<String> conclusions) {
        StringBuilder b = new StringBuilder();
        int i = 0;
        for(String c : conclusions) {
            i++;
            b.append(c);
            b.append(", ");
        }
        System.out.println("!SOLVE " + b);
    }

    @Override
    public void error(String message) {
        System.err.println("Error: " + message);
    }
}


public class Main {
    public static void main(String[] argv) {
        InMemoryStorage storage = new InMemoryStorage();
        App app = new App(storage, storage, new BruteForceSolver(), new ConsoleUi());
        if(argv.length > 0) {
            if(!app.loadFile(argv[0])) {
                return;
            }
        }
        app.run();
    }
}
