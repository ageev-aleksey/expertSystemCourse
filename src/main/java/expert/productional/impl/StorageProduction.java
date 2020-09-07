package expert.productional.impl;

import expert.productional.Functions;
import expert.productional.Production;
import expert.productional.Term;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

public class StorageProduction implements Production {

    WeakReference<InMemoryStorage.ProductionDescriptor> mProductionPtr;


    @Override
    public boolean isActivate(Iterable<Term> terms) {
        InMemoryStorage.ProductionDescriptor prod = get();
        int i = 0;
        for (Term t : terms) {
            if(prod.premises.contains(t)) {
                i++;
            }
        }
        return i == prod.premises.size();
    }

    @Override
    public String getName() {
        return get().name;
    }

    @Override
    public Set<Term> getPremises() {
        return new HashSet<Term>(get().premises);
    }

    @Override
    public Term getConclusion() {
        return get().conclusion;
    }

    public int hashCode() {
        return Functions.HashCode(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if( obj == null) return false;
        if(!(obj instanceof Production)) return false;
        Production ptr = (Production) obj;
        return  Functions.Equals(this, ptr);
    }

    private InMemoryStorage.ProductionDescriptor get() {
        InMemoryStorage.ProductionDescriptor prod = mProductionPtr.get();
        if(prod == null) {
            throw new RuntimeException("Dictionary already destroyed");
        }
        return prod;
    }
}
