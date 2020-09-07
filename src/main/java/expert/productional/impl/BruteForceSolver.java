package expert.productional.impl;

import expert.productional.*;
import expert.productional.Dictionary;

import java.util.*;

public class BruteForceSolver implements Solver {

    private WorkingMemory mMemory;

    @Override
    public Set<Production> solve(Dictionary dict, KnowledgeBase base, WorkingMemory mem) {
        Set<Term> terms = mem.getTerms();
        if (terms == null) {
            return Collections.emptySet();
        }
        Set<Production> activatedProduction = new HashSet<>();
        for(Term t : terms) {
            Set<Production> prods = base.getProductionByTerm(t.getName());
            for(Production p : prods) {
                if (p.isActivate(terms)) {
                    activatedProduction.add(p);
                }
            }
        }
        return activatedProduction;
    }
}
