package expert.productional;

import java.util.Iterator;
import java.util.Set;

public interface Production {
    /**
     * Проверка, активируется ли правила при заданном наборе фактов.
     * @param iterator итератор по набору фактов.
     * @return true если правило активируется, false иначе.
     */
    boolean isActivate(Iterable<Term> terms);

    /**
     * Возвращает название правила. Название назначаются автоматически.
     * таким образом не зарегистрированная правило в базе знаний (@see KnowledgeBase)
     * не имеет имени. В этом случае возращается пустая строка.
     * @return Имя зарегистрированого метода в базе знаний или пустая строка в ином случае.
     */
    String getName();

    /**
     * Множество посылок правила, к которым всегда применяется логическое И.
     * @return Возращает множество посылок
     */
    Set<Term> getPremises();

    /**
     *
     * @return Заключение из правила
     */
    Term getConclusion();

}
