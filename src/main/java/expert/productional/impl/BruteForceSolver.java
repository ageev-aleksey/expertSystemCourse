package expert.productional.impl;

import expert.productional.*;
import expert.productional.Dictionary;

import java.util.*;

public class BruteForceSolver implements Solver {

    private WorkingMemory mMemory;

    @Override
    public Set<Production> solve(Dictionary dict, KnowledgeBase base, WorkingMemory mem) {
        Set<Term> terms = mem.getTerms();
        Set<Term> update = new HashSet<>();
        if (terms == null) {
            return Collections.emptySet();
        }
        Set<Production> activatedProduction = new HashSet<>();

        boolean flag = true;
        while (flag) {
            for(Term t : terms) {
                Set<Production> prods = base.getProductionByTerm(t.getName());
                for(Production p : prods) {
                    if (!activatedProduction.contains(p) && p.isActivate(terms)) {
                        activatedProduction.add(p);
                        update.add(p.getConclusion());
                    }
                }
            }
            if(update.isEmpty()) {
                break;
            } else {
                for(Term el : update) {
                    terms.add(el);
                }
                update.clear();
            }
        }
        return activatedProduction;
    }
}
