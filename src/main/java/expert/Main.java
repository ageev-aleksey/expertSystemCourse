package expert;

import expert.alg.*;
import expert.parser.Parser;
import expert.parser.StepExecutor;
import expert.parser.Tokenizer;
import expert.productional.Dictionary;
import expert.productional.KnowledgeBase;
import expert.productional.impl.BruteForceSolver;
import expert.productional.impl.InMemoryStorage;
import expert.ui.ConsoleUi;
import expert.ui.Ui;
import expert.util.Empty;
import expert.util.Graph;
import expert.util.GraphToDot;
import expert.util.NodeIterator;

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
    public static void main(String[] argv) throws IOException {
//        InMemoryStorage storage = new InMemoryStorage();
//        App app = new App(storage, storage, new BruteForceSolver(), new ConsoleUi());
//        if(argv.length > 0) {
//            if(!app.loadFile(argv[0])) {
//                return;
//            }
//        }
//        app.run();
//    }

        Graph<Type, Empty> g = new Graph<>();
        Type t = new Type();
        t.isResolved = ResolvedFlags.NONE;
        t.nodeType = NodeType.MEDIUM;
        t.linksType = LinksType.OR;
        t.value = "n1";
        NodeIterator<Type, Empty> n1 = g.addNode(t);

        t = new Type();
        t.isResolved = ResolvedFlags.NONE;
        t.nodeType = NodeType.MEDIUM;
        t.linksType = LinksType.AND;
        t.value = "n2";
        NodeIterator<Type, Empty> n2 = g.addNode(t);

        t = new Type();
        t.isResolved = ResolvedFlags.NONE;
        t.nodeType = NodeType.FINAL;
        t.linksType = LinksType.OR;
        t.value = "n3";
        NodeIterator<Type, Empty> n3 = g.addNode(t);

        t = new Type();
        t.isResolved = ResolvedFlags.NONE;
        t.nodeType = NodeType.MEDIUM;
        t.linksType = LinksType.OR;
        t.value = "n4";
        NodeIterator<Type, Empty> n4 = g.addNode(t);

        t = new Type();
        t.isResolved = ResolvedFlags.NONE;
        t.nodeType = NodeType.MEDIUM;
        t.linksType = LinksType.AND;
        t.value = "n5";
        NodeIterator<Type, Empty> n5 = g.addNode(t);

        t = new Type();
        t.isResolved = ResolvedFlags.NONE;
        t.nodeType = NodeType.FINAL;
        t.linksType = LinksType.AND;
        t.value = "n6";
        NodeIterator<Type, Empty> n6 = g.addNode(t);

        t = new Type();
        t.isResolved = ResolvedFlags.NONE;
        t.nodeType = NodeType.FINAL;
        t.linksType = LinksType.AND;
        t.value = "n7";
        NodeIterator<Type, Empty> n7 = g.addNode(t);

        g.addLink(n1, n2);
        g.addLink(n1, n5);
        g.addLink(n2, n3);
        g.addLink(n2, n4);
        g.addLink(n5, n6);
        g.addLink(n5, n7);
        String dot = GraphToDot.convert(g);

        Pair<Boolean, Graph<Type, Empty>> res = FullBruteForce.run(g);


        String dot2 = GraphToDot.convert(g);
        FileWriter fw = new FileWriter("graph1.gv");
        fw.write(dot);
        fw.close();
        fw = new FileWriter("graph2.gv");
        fw.write(dot2);
        fw.close();
        ProcessBuilder graphiz = new ProcessBuilder("dot", "-Tsvg", "-o", "graph1.svg", "graph1.gv");
        graphiz.start();
        graphiz = new ProcessBuilder("dot", "-Tsvg", "-o", "graph2.svg", "graph2.gv");
        graphiz.start();
    }
}