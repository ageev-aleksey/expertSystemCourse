package expert.parser;

import expert.productional.Term;

public interface StepExecutor {
    boolean createRule(Iterable<String> conclusions, String premise);
    boolean createTerm(String name, String description);
    boolean terms();
    boolean rules();
    boolean solve(Iterable<String> conclusions);
}
