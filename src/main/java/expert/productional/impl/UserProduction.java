package expert.productional.impl;

import expert.productional.Functions;
import expert.productional.Production;
import expert.productional.Term;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserProduction implements Production {

    Set<Term> mPremises = new HashSet<>();
    Term mConclusion;

    public UserProduction(Iterable<Term> premises, Term conclusion) {
        Iterator<Term> itr = premises.iterator();
        while(itr.hasNext()) {
            Term t = itr.next();
            mPremises.add(t);
        }
        mConclusion = conclusion;
    }

    @Override
    public boolean isActivate(Iterable<Term> terms) {
        int i = 0;
        for (Term t : terms) {
            if (mPremises.contains(t)) {
                i++;
            }
        }
        return i == mPremises.size();
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Set<Term> getPremises() {
        return mPremises;
    }

    @Override
    public Term getConclusion() {
        return mConclusion;
    }

    public int hashCode() {
        return Functions.HashCode(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Production)) return false;
        Production ptr = (Production) obj;
       return  Functions.Equals(this, ptr);
    }

}
