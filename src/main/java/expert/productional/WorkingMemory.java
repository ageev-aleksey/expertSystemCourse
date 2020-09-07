package expert.productional;

import expert.productional.except.ExistsException;

import java.util.Iterator;
import java.util.Set;

/**
 * Рабочая память. Используется как временное хранилище во время работы решателя (@see Solver)
 * Хранит изначальные факты и факты выводимые решателем на основе Базы Знаний (@see KnowledgeBase)
 */
public interface WorkingMemory {
    /**
     * Добавление факта в рабочую память.
     * @param term Дабавляемый факт.
     */
    void addTerm(Term term);
    void addTerms(Iterator<Term> iterator);

    /**
     * Получение всех фактов из рабочей памяти.
     * @return Множество фактов.
     */
    Set<Term> getTerms();

    /**
     * Проверка, содержится ли факт в рабочей памяти.
     * @param term Проверяемый терм.
     * @return true - факт содержится, false - иначе.
     */
    boolean isContain(Term term);
}
