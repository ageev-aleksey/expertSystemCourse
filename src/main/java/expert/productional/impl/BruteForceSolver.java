package expert.productional.impl;

import expert.productional.*;
import expert.productional.Dictionary;

import java.util.*;

public class BruteForceSolver implements Solver {

    private WorkingMemory mMemory;

    /**
     * Функция выполняющая поиск по образцу.
     * @param dict - Множество термов
     * @param base - база знаний, набор правил
     * @param mem - Рабочая память. Это множество, в которое будут складываться все истинные факты
     * @param info
     * @return
     */
    @Override
    public Set<Production> solve(Dictionary dict, KnowledgeBase base, WorkingMemory mem, TraceInfo info) {
        Set<Term> terms = mem.getTerms(); // Извлекаем множесвто из рабочей памяти
        Set<Term> update = new HashSet<>(); // Вспомогательном множество, в которое будем складвать термы, которые выводятся из правил
        // на основе множества update - определяется момент, котгда новые факты перестают выводиться

        if (terms == null) { // Если рабочая память изначально пуста, то это означает, что мы не имеем ни каких знаний.
            // в этом случае мы не можем вывести какие то знания
            return Collections.emptySet();
        }
        Set<Production> activatedProduction = new HashSet<>(); // Множество уже активированных правил
        Set<Production> activatedProdEachStep = new HashSet<>(); // Множество нужно для пошагового вывода работы алгоритма в кносоль
        boolean flag = true; // маркер с помощью которого выполняется выход из алгоритма
        while (flag) {
            // Цикл по всем термам из рабочей памяти
            for(Term t : terms) {
                // Получаем множество продукций, которые содержат в совей левой части терм t
                Set<Production> prods = base.getProductionByTerm(t.getName());
                for(Production p : prods) { // цикл по множеству продукций prods
                    // Проверяем, содержится ли продукция p во множестве уже активированных продукций.
                    // Если оно там содержится, то пропускаем данную продукцию
                    // Иначе выполняем проверку, активируется ли продукция p на множестве термов terms (рабочая память)
                    if (!activatedProduction.contains(p) && p.isActivate(terms)) {
                        // В случае, если продукция активируется и она еще не находится во множестве активированных продукций
                        // то добавляем данную продукцию во множество активированных продукций.
                        activatedProduction.add(p);
                        update.add(p.getConclusion()); // Вывод из правила добавляем во множество update
                        if(info != null) { // данный код нужен для пошагового вывода работы алгоритма в консоль
                            activatedProdEachStep.add(p);
                        }
                    }
                }
            }
            // проверка множеста update на пустату
            if(update.isEmpty()) {
                // На данном шаге алгоритма мы не смогли вывести новые факты из правил.
                // Завершаем работу алгоритма, выходим из бесконечного цикла
                break;
            } else {
                // Если множество update не пусто - то это означает, что на текущем шаге алгоритма
                // мы смогли вывести из правил новые продукции
                if(info != null) { // данный код нужен для пошагового вывода раб
                    info.memoryVersions.add(new HashSet<>(terms));
                    info.productionalsSetVersions.add(new HashSet<>(activatedProdEachStep));
                    activatedProdEachStep.clear();
                }
                // Прекопируем все термы из множества update в рабочую память
                terms.addAll(update);
                update.clear(); // очищаем множество
            }

        }
        // activatedProduction - содержит списко продукций, которые были активированы
        // так же данный метод имеет побочный эффект. Внутри функции мы обновляли рабочую память (переменная terms)
        // она так же будет обновлена снаружи, у вызывающего кода.
        return activatedProduction;
    }
}
