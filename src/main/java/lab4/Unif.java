package lab4;

import expert.alg.Pair;
import expert.parser.Parser;

public class Unif {
    enum Type {
        CONSTANT, VARIABLE, FUNCTION, NONE,
    }

    public static Predicate apply(Predicate a, Predicate b) {
        // Проверяем имена предикатов
        if (!a.equals(b)) {
            // Если предикаты имеют одинакове имена то у нх обязано совпадать количество аргументов
            return null; // Предикаты должны иметь одинаковые имена
        }
        // Проверяем, что число аргументов совпадает
        int argsNum = a.args().size();
        if (a.args().size() != b.args().size()) {
            return null; // Предикаты не унифицируются
        }

        // Выполняем сравнение аргументов предикатов
        for (int k = 0; k < argsNum; k++) {
            Term ta = a.args().get(k);
            Term tb = b.args().get(k);
            Pair<Term, Term> sub = findSubstitution(ta, tb);
            // Приминение подстановки
            for (int j = 0; j < argsNum; j++) {
                //if (fullTermEquals())
            }

        }
        return null;
    }

    static Pair<Term, Term> findSubstitution(Term a, Term b) { // {Что заменяем, на что заменяем}
        Type tta = getType(a);
        Type ttb = getType(b);

        if (tta.equals(Type.VARIABLE) && ttb.equals(Type.VARIABLE)) {
            // выполняем замену одной переменной на другую
        }

        if (tta.equals(Type.VARIABLE) && ttb.equals(Type.CONSTANT)
                || tta.equals(Type.CONSTANT) && ttb.equals(Type.VARIABLE)) {
            // выполняем замену переменной на константу
        }

        if (tta.equals(Type.VARIABLE) && ttb.equals(Type.FUNCTION)
                || tta.equals(Type.FUNCTION) && ttb.equals(Type.VARIABLE) ) {
            // Необходимо проверить, что переменная не содержится в функцииэ
            // Если проверка прошла успешно, то заменяем переменную на функцию
        }

        if (tta.equals(Type.FUNCTION) && ttb.equals(Type.FUNCTION)) {
            // Проверяем, что функции имеют одинакове имя, рекурсивно проверяем внутренности функций
            Function af = (Function) a;
            Function bf = (Function) b;
            return functionCheck(af, bf);
        }
        return null;
    }

    static Predicate applySubstitution(Pair<Term, Term> sub, Predicate pred) {
        int numArgs = pred.args().size();
        Type subType = getType(sub.first);
        for (int i = 0; i < numArgs; i++) {
            Term t = pred.args().get(i);
            Type ttype = getType(t);

            if (ttype.equals(Type.VARIABLE)) {
                // Выполняем простую замену
            }

            if (ttype.equals(Type.FUNCTION)) {
                // Сама функция не заменяется. Заменяется ее внутренности
            }

            if (ttype.equals(Type.CONSTANT)) {
                // ОШИБКА!! - константу заменить нельзя!
            }

        }
        return null;
    }


    static Type getType(Term t) {
        Class c = t.getClass();
        if (Variable.class.equals(c)) {
            return  Type.VARIABLE;
        }
        if (Constant.class.equals(c)) {
            return Type.CONSTANT;
        }
        if (Function.class.equals(c)) {
            return Type.FUNCTION;
        }
        return Type.NONE;
    }

    static Pair<Term, Term> functionCheck(Function a, Function b) { // {Что заменяем, на что заменяем}
        if (a.name().equals(b.name())) { // Функции имеют одинаковые имена
            for (int i = 0; i < a.args().size(); i++) { // проверяем аргументы функции
                Pair<Term, Term> substitution = findSubstitution(a.arg(i), b.arg(i));
                if (substitution == null) { // Аргуметы по не поддаются унификации
                    return null; // функция не поддается унификации
                }
            }
        }
        return null;
    }

}
