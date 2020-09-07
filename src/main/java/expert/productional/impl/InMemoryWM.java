package expert.productional.impl;

import expert.productional.Term;
import expert.productional.WorkingMemory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class InMemoryWM implements WorkingMemory {

    private HashSet<Term> mTerms = new HashSet<>();

    @Override
    public void addTerm(Term term) {
        mTerms.add(term);
    }

    @Override
    public void addTerms(Iterator<Term> iterator) {
        while(iterator.hasNext()) {
            Term t = iterator.next();
            mTerms.add(t);
        }
    }

    @Override
    public Set<Term> getTerms() {
        return mTerms;
    }

    @Override
    public boolean isContain(Term term) {
        return false;
    }
}
