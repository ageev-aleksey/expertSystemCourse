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

    /**
     * Создание продукции которая состоит из фактов объдиненных логической связкой "И"
     * @param premises - список термов, левой части правила.
     * @param conclusion - заключение (правая часть правила)
     */
    public UserProduction(Iterable<Term> premises, Term conclusion) {
        Iterator<Term> itr = premises.iterator();
        while(itr.hasNext()) {
            Term t = itr.next();
            mPremises.add(t);
        }
        mConclusion = conclusion;
    }

    /**
     * Проверка, активируется ли данное правило на некотором множестве фактов
     * @param terms - множество фактов
     * @return
     */
    @Override
    public boolean isActivate(Iterable<Term> terms) {
        int i = 0;
        // Цикл по всем фактам из множества
        for (Term t : terms) {
            if (mPremises.contains(t)) {
                i++;
            }
        }
        // Если число совпадаений элементов из множества с элементами из списка
        // термов левой части продукции совпадает, то это означает, что правило активируется
        return i == mPremises.size();
    }

    /**
     * Функция возращающая имя правила
     * Данный класс описывает правило еще не зарегестрированнае в базе знаний.
     * Поэтому у правила нету имени. Имена назначает база знаний.
     * @return
     */
    @Override
    public String getName() {
        return "";
    }

    /**
     * Получение множества поссылок правлиа (его левая часть)
     * @return
     */
    @Override
    public Set<Term> getPremises() {
        return mPremises;
    }

    /**
     * Получение заключения из правлиа (его правая часть)
     * @return
     */
    @Override
    public Term getConclusion() {
        return mConclusion;
    }

    /**
     * Хеш-функция
     * Необходима для хранения правил в хеш-табице (способ реализации множества)
     * @return
     */
    public int hashCode() {
        return Functions.HashCode(this);
    }

    /**
     * Функция сравнения правил между собой
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Production)) return false;
        Production ptr = (Production) obj;
       return  Functions.Equals(this, ptr);
    }

}
