package expert;

import expert.parser.StepExecutor;
import expert.ui.ConsoleUi;

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
//        String text1 = "machine(good work); people(bio entity); RULES; TERMS; work(oj); machine, people > work;";
//        String text2 = "machine, people > work; machine, people, game > not_work;";
//        String text = text1;
//        Tokenizer tokenizer = new Tokenizer(text);
//        Parser p = new Parser();
//        Executor exe = new Executor();
//        p.parse(tokenizer, exe);

          ConsoleUi ui = new ConsoleUi();
          ui.run();

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
