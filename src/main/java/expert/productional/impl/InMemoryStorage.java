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

/**
 * Класс реализующий базу знаний и множество фактов в одном месте
 */
public class InMemoryStorage implements Dictionary, KnowledgeBase {

    /**
     * Описатель правила
     */
    static class ProductionDescriptor {
        /**
         * Список предпосылок (левая часть правлиа)
         */
        List<Term> premises = new LinkedList<>();
        /**
         * Заключение правила (правая часть правлиа)
         */
        Term conclusion;
        /**
         * Имя правила
         */
        String name;
    }

    /**
     * Описатель терма, который зарегистрирован в базе знаний
     */
    static class Node {
        List<ProductionDescriptor> inProductions; // список продукций в которых находится факт
        Term term; // факт
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

    /**
     * Получение множества термов из базы знаний
     * @return
     */
    @Override
    public Set<Term> getTerms() {
        Set<Term> terms = new HashSet<>();
        for(String el : mDict.keySet()) {
            terms.add(initDTerm(el));
        }
        return terms;
    }

    /**
     *
     * @param term Добавлаяемый факт в словарь (после выполнения переменная указывает на незарегистрированный факт)
     * @return
     * @throws ExistsException
     */
    @Override
    public Term addTerm(Term term) throws ExistsException {
        String name = term.getName();
        if(mDict.containsKey(name)) {
            throw new ExistsException("term with name " + name + "already exists in dictionary.");
        }
        mDict.put(name, new Node(term));
        return initDTerm(name);
    }

    /**
     * Получение факта по его названию
     * @param termName Название факта.
     * @return
     * @throws NotFoundException
     */
    @Override
    public Term getTerm(String termName) throws NotFoundException {
        Node res = mDict.get(termName);
        if(res == null) {
            throw new NotFoundException("Not found term by name " + termName + " in dictionary.");
        }
        return initDTerm(termName);
    }

    /**
     * ПРоверка, содержится ли факт с данным названием в базе знаний
     * @param termName название факта.
     * @return
     */
    @Override
    public boolean isContain(String termName) {
        return mDict.containsKey(termName);
    }

    /**
     * Получение продукции по названию
     * @param name Название продукции.
     * @return
     * @throws NotFoundException
     */
    @Override
    public Production getProduction(String name) throws NotFoundException {
        ProductionDescriptor prod = mProductions.get(name);
        if(prod == null) {
            throw new NotFoundException("Not found exception by name + "+ name + " in KnowledgeBase");
        }
        return initDProduction(prod);
    }

    /**
     * Добавление продукции. Все термы внутри продукции должны быть зараенне зарегистрированы в базе знаний
     * @param production добавляемая продукция
     * @throws ExistsException
     * @throws InvalidProduction
     */
    @Override
    public void addProduction(Production production) throws ExistsException, InvalidProduction {
        List<Node> foundedTerms = new LinkedList<>();
        // Цикл по термам из которых состоит продукция
        for (Term t : production.getPremises()) {
            // Выполняем проверку, что терм t зарегестрирован в базе знаний
            Node n = mDict.get(t.getName());
            if (n == null) {
                throw new InvalidProduction("Production containing not registered Terms");
            }
            foundedTerms.add(n);
        }
        // проверяем, что заключение из правила зарегистрирована в базе знанйи
        Term conclusion = production.getConclusion();
        Node regCon = mDict.get(conclusion.getName());
        if(regCon == null) {
            throw new InvalidProduction("Production containing not registered Terms");
        }
        // Формируем описатель для нового правила
        ProductionDescriptor nProd = new ProductionDescriptor();
        for(Node el : foundedTerms) {
            el.inProductions.add(nProd); // Добавлем в описать терма, что он содержится в добавляемом правиле
            nProd.premises.add(el.term); // Добавлем описатель правила, терм. который содержится в правиле
        }
        nProd.conclusion = regCon.term; // добавляем в описатель правлиа заключение из правлиа
        String prodName = "P" + mLastProdIndex; // формируем имя для правила
        nProd.name = prodName;
        mProductions.put(prodName, nProd); // Кладем описатель правила в базу знаний
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
