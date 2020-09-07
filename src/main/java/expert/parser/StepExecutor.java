package expert.parser;

import expert.productional.Term;

public interface StepExecutor {
    void createRule(Iterable<String> conclusions, String premise);
    void createTerm(String name, String description);
    void terms();
    void rules();
    void solve(Iterable<String> conclusions);
    void error(String message);
}
