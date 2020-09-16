package expert.productional.impl;

import expert.productional.*;
import expert.productional.Dictionary;

import java.util.*;

public class BruteForceSolver implements Solver {

    private WorkingMemory mMemory;

    @Override
    public Set<Production> solve(Dictionary dict, KnowledgeBase base, WorkingMemory mem, TraceInfo info) {
        Set<Term> terms = mem.getTerms();
        Set<Term> update = new HashSet<>();
        if (terms == null) {
            return Collections.emptySet();
        }
        Set<Production> activatedProduction = new HashSet<>();
        Set<Production> activatedProdEachStep = new HashSet<>();
        boolean flag = true;
        while (flag) {
            for(Term t : terms) {
                Set<Production> prods = base.getProductionByTerm(t.getName());
                for(Production p : prods) {
                    if (!activatedProduction.contains(p) && p.isActivate(terms)) {
                        activatedProduction.add(p);
                        update.add(p.getConclusion());
                        if(info != null) {
                            activatedProdEachStep.add(p);
                        }
                    }
                }
            }
            if(update.isEmpty()) {
                break;
            } else {
                if(info != null) {
                    info.memoryVersions.add(new HashSet<>(terms));
                    info.productionalsSetVersions.add(new HashSet<>(activatedProdEachStep));
                    activatedProdEachStep.clear();
                }
                terms.addAll(update);
                update.clear();
            }

        }
        return activatedProduction;
    }
}
