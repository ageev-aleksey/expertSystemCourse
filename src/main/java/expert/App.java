package expert;


import com.sun.tools.javac.util.Pair;
import expert.parser.Parser;
import expert.parser.StepExecutor;
import expert.parser.Tokenizer;
import expert.productional.*;
import expert.productional.except.ExistsException;
import expert.productional.except.InvalidProduction;
import expert.productional.except.NotFoundException;
import expert.productional.impl.InMemoryWM;
import expert.productional.impl.UserProduction;
import expert.productional.impl.UserTerm;
import expert.ui.Ui;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class App implements StepExecutor {

    Dictionary mDict;
    KnowledgeBase mBase;
    Solver mSolver;
    Ui mCommandGetter;

    enum CommandType {
        ES_COMMAND(true), // команда предназначена для решателя
        IO_COMMAND(true), // команда предназначена для оболочки
        NONE(false); // не является командной

        boolean is() {
            return mIs;
        }

        CommandType(boolean b) {
            mIs = b;
        }
        private boolean mIs;
    }

    App(Dictionary dict, KnowledgeBase base, Solver solver, Ui ui) {
        mDict = dict;
        mBase = base;
        mSolver = solver;
        mCommandGetter = ui;
    }


    public void run() {
        printHello();
        mCommandGetter.displayMessage("Expert system v1");
        Pair<String, CommandType> in;
        Parser p = null;
        Tokenizer t = null;
        while (true) {
            in = input();
            if(in == null) continue;
            if(in.snd == CommandType.IO_COMMAND) {
                if("\\q".equals(in.fst)) {
                    break;
                } else if ("\\h".equals(in.fst)){
                   printHelp();
                } else {
                    mCommandGetter.displayMessage("Error: Invalid command. Use \\h for help.");
                }
            } else if (in.snd == CommandType.ES_COMMAND) {
                t = new Tokenizer(in.fst);
                p = new Parser();
                p.parse(t, this);
            }
        }
    }
    private Pair<String, CommandType> input() {
        StringBuilder line = mCommandGetter.getCommand();
        if(line.length() == 0) return null;
        CommandType type;
        while(!(type = isCommand(line)).is()) line = mCommandGetter.tryGetCommand();
        return new Pair<String, CommandType>(line.toString().trim(), type);
    }

    private CommandType isCommand(CharSequence sequence) {
        int i = 0;
        for(; i < sequence.length() && sequence.charAt(i) == ' '; i++ ) {}
        if(sequence.charAt(i) == '\\') {
            return CommandType.IO_COMMAND;
        }
            i = sequence.length()-1;
        for(; i >= 0 && sequence.charAt(i) == ' '; i-- ) {}
        if(sequence.charAt(i) == ';') {
            return CommandType.ES_COMMAND;
        } else {
            return CommandType.NONE;
        }
    }






    @Override
    public void createRule(Iterable<String> conclusions, String premise) {
        List<Term> terms = new LinkedList<>();
        Term pterm = null;
        boolean done = true;
        try {
            for(String c : conclusions) {
                Term t = mDict.getTerm(c);
                terms.add(t);
            }
            pterm = mDict.getTerm(premise);

        } catch (NotFoundException exp) {
            done = false;
            mCommandGetter.displayMessage("Error: " + exp.getMessage());
        }

        if (done) {
            try{
                mBase.addProduction(new UserProduction(terms, pterm));
            } catch(ExistsException exp) {
                mCommandGetter.displayMessage("Error: This production already exists");
            } catch (InvalidProduction exp) {
                mCommandGetter.displayMessage("Fatal error: " + exp.getMessage());
            }
        }

    }

    @Override
    public void createTerm(String name, String description) {
        Term term = new UserTerm(name, description);
        try {
            mDict.addTerm(term);
        } catch(ExistsException exp) {
            mCommandGetter.displayMessage("Error: Term already exists");
        }

    }

    @Override
    public void terms() {
        StringBuilder b = new StringBuilder();
        Set<Term> terms = mDict.getTerms();
        for(Term t : terms) {
            termToString(b, t);
        }
        mCommandGetter.displayMessage(b.toString());
    }

    @Override
    public void rules() {
        Set<Production> prods = mBase.getProductions();
        StringBuilder b = new StringBuilder();
        for(Production el : prods) {
            b.append(el.getName())
                    .append(": ");
            for(Term t : el.getPremises()) {
                b.append(t.getName()).append(", ");
            }
            b.setLength(b.length()-2);
            b.append(" > ")
                    .append(el.getConclusion().getName())
                    .append(";\n");
        }
        mCommandGetter.displayMessage(b.toString());
    }

    @Override
    public void solve(Iterable<String> conclusions) {
        String cc = null;
        try {
            WorkingMemory mem = new InMemoryWM();
            for (String c : conclusions) {
                cc = c;
                mem.addTerm(mDict.getTerm(c));

            }
            Set<Production> prods = mSolver.solve(mDict, mBase, mem);
            Set<Term> terms = new HashSet<>();
            for(Production p : prods) {
                terms.add(p.getConclusion());
            }
            if (prods.isEmpty()) {
                mCommandGetter.displayMessage("Not result");
            } else {
                StringBuilder b = new StringBuilder();
                b.append("-=Results=-\n");
                for (Term t : terms) {
                    termToString(b, t);
                }
                mCommandGetter.displayMessage(b.toString());
            }

        } catch (NotFoundException exp) {
            mCommandGetter.displayMessage("Not found term -> " + cc);
        }

    }

    @Override
    public void error(String message) {
        mCommandGetter.displayMessage("Parsing error: " + message);
    }

    private void termToString(StringBuilder sb, Term term) {
        sb.append("- ")
                .append(term.getName())
                .append("(")
                .append(term.getDescription())
                .append(")\n");
    }

    private void printHelp() {
        try {
            String s = readStringResource("help.txt");
            mCommandGetter.displayMessage(s);
        } catch (IOException e) {
            mCommandGetter.displayMessage("Internal Error: " + e.getMessage());
        }
    }

    private void printHello() {
        try {
            String s = readStringResource("hello.txt");
            mCommandGetter.displayMessage(s);
        } catch (IOException e) {
            mCommandGetter.displayMessage("Internal Error: " + e.getMessage());
        }
    }

    private String readStringResource(String rname) throws IOException {
        StringBuilder b = new StringBuilder();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(rname);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (reader.ready()) {
            b.append(reader.readLine()).append("\n");
        }
        return b.toString();
    }

    public  boolean loadFile(String path) {
        Path p = Paths.get(path);
        try {
            File f = new File(p.toString());
            BufferedReader r = new BufferedReader(new FileReader(f));
            StringBuilder buffer = new StringBuilder();
            while(r.ready()) {
                buffer.append(r.readLine());
            }

            Tokenizer t = new Tokenizer(buffer.toString());
            Parser parser = new Parser();
            parser.parse(t, this);
            mCommandGetter.displayMessage("Loaded " + mDict.getTerms().size() + " Terms;\n " +
                    "Loaded " + mBase.getProductions().size() + " Productions;\n");

        } catch (FileNotFoundException exp) {
            System.out.println("File not found: " + exp.getMessage());
            return false;
        } catch (IOException exp) {
            System.out.println("Error reading file: " + exp.getMessage());
            return false;
        }
        return true;
    }
}
