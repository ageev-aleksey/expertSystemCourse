package expert.productional;

import expert.productional.except.ExistsException;
import expert.productional.except.NotFoundException;

import java.util.Optional;
import java.util.Set;

/**
 * Словарь фактов предметной области. На основе фактов из данного словаря строится
 * База Знаний (@see KnowledgeBase).
 */
public interface Dictionary {
    /**
     * @return Множество теромов содержащий словарь
     */
    Set<Term> getTerms();

    /**
     * Добавление факта в словарь.
     * @param term Добавлаяемый факт в словарь (после выполнения переменная указывает на незарегистрированный факт)
     * @throws ExistsException При условии, что в словаре уже существует факт с таким же названием
     * @return Экземпляр зарегистрированного факта в словаре,
     * факт передающийся в качестве аргумента не является зарегистрированным фактом
     */
    Term addTerm(Term term) throws ExistsException;

    /**
     * Получение фатка из словоря по названию.
     * @param termName Название факта.
     * @return Если факт существует то он будет возращен
     * @throws NotFoundException Если факт с заданным названием отсутсвтует
     */
    Term getTerm(String termName) throws NotFoundException;
    /**
     * Провекра на существование факта по его названию
     * @param termName название факта.
     * @return true - если факт с заданным названеим содержится в словаре, false - иначе
     */
    boolean isContain(String termName);

}
