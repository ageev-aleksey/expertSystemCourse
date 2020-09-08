package expert.productional.impl;

import expert.productional.Dictionary;
import expert.productional.KnowledgeBase;
import expert.productional.Production;
import expert.productional.Term;
import expert.productional.except.ExistsException;
import expert.productional.except.InvalidProduction;
import expert.productional.except.NotFoundException;

import java.lang.ref.WeakReference;
import java.util.*;

public class InMemoryStorage implements Dictionary, KnowledgeBase {

    static class ProductionDescriptor {
        List<Term> premises = new LinkedList<>();
        Term conclusion;
        String name;
    }

    static class Node {
        List<ProductionDescriptor> inProductions; // список продукций в которых находится факт
        Term term;
        Node(Term t) {
            term = t;
            inProductions = new LinkedList<>();
        }
    }

    HashMap<String, Node> mDict = new HashMap<>(); // Набор фактов
    HashMap<String, ProductionDescriptor> mProductions = new HashMap<>(); // набор правил
    int mLastProdIndex = 0;


    private Term initDTerm(String name) {
        DictionaryTerm t = new DictionaryTerm();
        t.mDict = new WeakReference<>(this);
        t.mName = name;
        return t;
    }
    private Production initDProduction(ProductionDescriptor prod) {
        StorageProduction res = new StorageProduction();
        res.mProductionPtr = new WeakReference<>(prod);
        return res;
    }

    @Override
    public Set<Term> getTerms() {
        Set<Term> terms = new HashSet<>();
        for(String el : mDict.keySet()) {
            terms.add(initDTerm(el));
        }
        return terms;
    }

    @Override
    public Term addTerm(Term term) throws ExistsException {
        String name = term.getName();
        if(mDict.containsKey(name)) {
            throw new ExistsException("term with name " + name + "already exists in dictionary.");
        }
        mDict.put(name, new Node(term));
        return initDTerm(name);
    }

    @Override
    public Term getTerm(String termName) throws NotFoundException {
        Node res = mDict.get(termName);
        if(res == null) {
            throw new NotFoundException("Not found term by name " + termName + " in dictionary.");
        }
        return initDTerm(termName);
    }

    @Override
    public boolean isContain(String termName) {
        return mDict.containsKey(termName);
    }

    @Override
    public Production getProduction(String name) throws NotFoundException {
        ProductionDescriptor prod = mProductions.get(name);
        if(prod == null) {
            throw new NotFoundException("Not found exception by name + "+ name + " in KnowledgeBase");
        }
        return initDProduction(prod);
    }

    @Override
    public void addProduction(Production production) throws ExistsException, InvalidProduction {
        List<Node> foundedTerms = new LinkedList<>();
        for (Term t : production.getPremises()) {
            Node n = mDict.get(t.getName());
            if (n == null) {
                throw new InvalidProduction("Production containing not registered Terms");
            }
            foundedTerms.add(n);
        }
        Term conclusion = production.getConclusion();
        Node regCon = mDict.get(conclusion.getName());
        if(regCon == null) {
            throw new InvalidProduction("Production containing not registered Terms");
        }
        ProductionDescriptor nProd = new ProductionDescriptor();
        for(Node el : foundedTerms) {
            el.inProductions.add(nProd);
            nProd.premises.add(el.term);
        }
        nProd.conclusion = regCon.term;
        String prodName = "P" + mLastProdIndex;
        nProd.name = prodName;
        mProductions.put(prodName, nProd);
        mLastProdIndex++;
    }

    @Override
    public Set<String> namesProductions() {
        return mProductions.keySet();
    }

    @Override
    public Set<Production> getProductionByTerm(String termName) {
        Node nt = mDict.get(termName);
        if(nt == null) {
            return Collections.emptySet();
        }
        HashSet<Production> prods = new HashSet<>();
        for(ProductionDescriptor el : nt.inProductions) {
            prods.add(initDProduction(el));
        }
        return prods;
    }

    @Override
    public Set<Production> getProductions() {
        Set<Production> prods = new HashSet<>();
        for(Map.Entry<String, ProductionDescriptor> el : mProductions.entrySet()) {
            prods.add(initDProduction(el.getValue()));
        }
        return prods;
    }
}
