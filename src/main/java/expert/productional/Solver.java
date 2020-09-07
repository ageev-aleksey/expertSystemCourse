package expert.productional;

import java.util.Iterator;
import java.util.Set;

public interface Solver {
    /**
     * Поиск решения по базе знаний (@see KnowledgeBase), на основе заданных фактов (@see Term)
     * @param iterator итератор по набору фактов (@see Term)
     * @return Набор фактов, которыя являются следствием исходных фактов
     */
    Set<Production> solve(Dictionary dict, KnowledgeBase base, WorkingMemory mem);

}
