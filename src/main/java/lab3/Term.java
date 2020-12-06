package lab3;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Реализация понятия терма:
 *  - Простое высказывание является термом
 *  - Объединение термов через логичесике операции является термом
 *
 *  Класс реализует только 2 логические операции:
 *  - Логическое ИЛИ
 *  - Логическое отрицание
 */
public class Term {
    final public static String ZERO_TERM = "[ZERO]";
    /// Связанный список термов, которые определяют дизьюнкт
    private List<Term> m_lDisjunctions;
    /// Применятся ли операция логического отрицания к дизьюнкту
    boolean m_bApplyNegation;
    /// Если данный терм описывает простое высказывание, то
    /// используется это поле для определения имени простого высказывания
    /// дизьюнкт не имеет имени, так как состоит постых высказываний
    String m_sName;

    /**
     * КОнструктор простого высказывания
     * @param name - название высказывания
     */
   public Term(String name) {
       m_lDisjunctions = Collections.emptyList();
       m_sName = name;
       m_bApplyNegation = false;
    }

    /**
     * Конструктор создающий дизьюнкт из списка термов
     * @param lDisjunctions
     */
    public Term(List<Term> lDisjunctions) {
       m_lDisjunctions = new LinkedList<Term>();
       m_lDisjunctions.addAll(lDisjunctions);
       m_sName = "";
       m_bApplyNegation = false;
    }

    /**
     * ПРиватный конструктор, который ни чего не далает
     */
    private Term() {}

    /**
     * Применение операции логического отрицания к терму.
     * При этом функция создает новый терм с отрицанием, исходный терм не изменяется
     * @return терм с отрицанием
     */
    Term not() {
        Term negativeTerm = new Term();
        if (m_lDisjunctions.isEmpty()) {
            negativeTerm.m_lDisjunctions = Collections.emptyList();
            negativeTerm.m_sName = m_sName;

        } else {
            negativeTerm.m_lDisjunctions = new LinkedList<>();
            negativeTerm.m_lDisjunctions.addAll(m_lDisjunctions);
            negativeTerm.m_sName = "";
        }
        negativeTerm.m_bApplyNegation = !m_bApplyNegation;
        return negativeTerm;
    }

    /**
     * Операция логического ИЛИ.
     * Создается новый терм, который сосотоит из двух термов объединенные дизьюнкцией
     * @param otherTerm
     * @return
     */
    Term or(Term otherTerm) {
        Term tRes = new Term();
        tRes.m_bApplyNegation = false;
        tRes.m_sName = "";
        tRes.m_lDisjunctions = new LinkedList<Term>();
        tRes.m_lDisjunctions.add(this);
        tRes.m_lDisjunctions.add(otherTerm);
        return tRes;
    }

    /**
     * Получение имя терма.
     * Если терм не является простым высказыванеим, то будет возвращаена пустая строка
     * @return
     */
    public String getName() {
        return m_sName;
    }

    /**
     * Проверка: Приминена ли операция отрецания к данному терму
     * @return
     */
    public boolean isNeg() {
        return m_bApplyNegation;
    }

    /**
     * Получение списка простых высказываний из Терма
     * @return
     */
    public List<Term> getSimple() {
        List<Term> lRes = new LinkedList<>();
        if (m_lDisjunctions.isEmpty()) {
            lRes.add(this);
        } else {
            for (Term t : m_lDisjunctions) {
                List<Term> lt = t.getSimple();
                lRes.addAll(lt);
            }
        }
        return lRes;
    }
    /**
     * Создание пустого терма. Используется для определения нулевого дизъюнкта
     * @return
     */
    public static Term createZeroTerm() {
        Term t = new Term();
        t.m_lDisjunctions = Collections.emptyList();
        t.m_sName = "[ZERO]";
        t.m_bApplyNegation = false;
        return t;
    }

    /**
     * Преобразование терма в текст
     * @return
     */
    public String toString() {
        StringBuilder sbTerm = new StringBuilder();
        if (m_bApplyNegation) {
            sbTerm.append("-(");
        }
        if (m_lDisjunctions.isEmpty()) {
            sbTerm.append(m_sName);
        } else {
            int len = m_lDisjunctions.size() - 1;
            Iterator<Term> itr = m_lDisjunctions.iterator();
            for (int i = 0; i < len; ++i) {
                Term el = itr.next();
                sbTerm.append(el.toString());
                sbTerm.append("+");
            }
            Term endTerm = itr.next();
            sbTerm.append(endTerm.toString());
        }

        if (m_bApplyNegation) {
            sbTerm.append(")");
        }
        return sbTerm.toString();
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Term term = (Term) obj;
        return toString().equals(obj.toString());
    }
}


