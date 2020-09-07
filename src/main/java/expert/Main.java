package expert;

import expert.parser.TokenType;
import expert.parser.Tokenizer;
import com.sun.tools.javac.util.Pair;
import expert.productional.Production;
import expert.productional.Solver;
import expert.productional.Term;
import expert.productional.except.ProductionalException;
import expert.productional.impl.*;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] argv) {
        String text = "machine(good work); people(bio entity); RULES; TERMS; work(oj); machine, people > work;";
        Tokenizer tokenizer = new Tokenizer(text);

        Pair<TokenType, String> tok = tokenizer.next();
        while(tok.fst != TokenType.END) {
            System.out.println(tok.fst + " : " + tok.snd );
            tok = tokenizer.next();
        }

//        InMemoryStorage storage = new InMemoryStorage();
//        try {
//            Term t1 = storage.addTerm(new UserTerm("t1", "t1"));
//            Term t2 = storage.addTerm(new UserTerm("t2", "t2"));
//            Term t3 = storage.addTerm(new UserTerm("t3", "t3"));
//            Term t4 = storage.addTerm(new UserTerm("t4", "t4"));
//            storage.addProduction(new UserProduction(Arrays.asList(t1, t2, t3), t4));
//            storage.addProduction(new UserProduction(Arrays.asList(t1, t2, t4), t3));
//            storage.addProduction(new UserProduction(Arrays.asList(t1, t3), t2));
//
//            InMemoryWM w = new InMemoryWM();
//            w.addTerm(t1);
//            w.addTerm(t2);
//            w.addTerm(t3);
//
//            Solver s = new BruteForceSolver();
//            Set<Production> res = s.solve(storage, storage, w);
//
//            System.out.println("Result:");
//            for (Production p : res) {
//                System.out.println(p.getConclusion().getName());
//            }
//
//        } catch(ProductionalException exp) {
//            System.out.println(exp.getMessage());
//        }


    }
}
