package expert.productional;

import expert.productional.except.ExistsException;
import expert.productional.except.InvalidProduction;
import expert.productional.except.NotFoundException;

import java.util.List;
import java.util.Set;

/**
 * База знаний содержащая набор правил. Каждое правило состояит из фактов содержащихся в Словаре (@see Dictionary)
 */
public interface KnowledgeBase {
    /**
     * Получить продукцию по ее названию.
     * @param name Название продукции.
     * @return Продукция.
     * @throws NotFoundException При отсуствии продукции с заданным названием.
     */
    Production getProduction(String name) throws NotFoundException;

    /**
     * Добавление продукции в базу знаний
     * @param production добавляемая продукция
     * @throws ExistsException При условии, что добавляемая продукцися существует в базе знаний
     */
    void addProduction(Production production) throws ExistsException, InvalidProduction;
    Set<String> namesProductions();

    /**
     * Получить множество правил в которые воходи заданный факт в качетсве посылки
     * @return Правила в которые входит заданный факт
     */
    Set<Production> getProductionByTerm(String termName);
    Set<Production> getProductions();
}
