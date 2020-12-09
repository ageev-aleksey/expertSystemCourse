package lab4;

import expert.alg.Pair;
import expert.parser.Parser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Unif {
    enum Type {
        CONSTANT, VARIABLE, FUNCTION, NONE,
    }

    private List<Pair<Term, Term>> m_unifier = new LinkedList<>();

    /**
     * Метод выполняет унификацию двух предикатов.
     * Если это возможно, то возращает новый унифицированный предикат
     * и унификатор (унификатор представлет из себя список пар {что заменить, на что заменить})
     * @param pa предикат для унификации
     * @param pb предикат для унификации
     * @return Унифицированный предикат и унификатор
     */
    public  Pair<Predicate, Unifier> apply(Predicate pa, Predicate pb) {
        Predicate a = pa.clone();
        Predicate b = pb.clone();
        m_unifier = new LinkedList<>();
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
            if (sub == null) {
                return null;
            }
            // если замена самого на себя, то пропускаем
            if (sub.first.equals(sub.second)) {
                continue;
            }
            // Приминение подстановки
            applySubstitution(sub, a);
            applySubstitution(sub, b);
            //unifier.add(sub);
        }
        Pair<Predicate, Unifier> ret = new Pair<>();
        ret.first = a;
        ret.second = new Unifier(m_unifier);
        return ret;
    }

    public  static Predicate applyUnifier(Predicate p,  List<Pair<Term, Term>> unifier) {
        Predicate ret = p.clone();
        for (Pair<Term, Term> sub : unifier) {
            applySubstitution(sub, ret);
        }
        return ret;
    }

    private  Pair<Term, Term> findSubstitution(Term a, Term b) { // {Что заменяем, на что заменяем}
        Type tta = getType(a);
        Type ttb = getType(b);
        Pair<Term, Term> ret = new Pair<>();
        boolean isOk = false;

        if ((tta == Type.VARIABLE) && (ttb == Type.VARIABLE)) {
            // выполняем замену одной переменной на другую
            ret.first = a;
            ret.second = b;
            isOk = true;
        } else if (tta == Type.VARIABLE && ttb == Type.CONSTANT) {
            // выполняем замену переменной на константу
            ret.first = a;
            ret.second = b;
            isOk = true;
        } else if (tta == Type.CONSTANT && ttb == Type.VARIABLE) {
            // выполняем замену переменной на константу
            ret.first = b;
            ret.second = a;
            isOk = true;
        } else if (tta == Type.VARIABLE && ttb == Type.FUNCTION) {
            // Необходимо проверить, что переменная не содержится в функцииэ
            // Если проверка прошла успешно, то заменяем переменную на функцию
            if (variableDontArgOfFunction((Function)b, (Variable)a)) {
                ret.first = a;
                ret.second = b;
                isOk = true;
            }
        } else if (tta == Type.FUNCTION && ttb == Type.VARIABLE) {
            // Необходимо проверить, что переменная не содержится в функцииэ
            // Если проверка прошла успешно, то заменяем переменную на функцию
            if (variableDontArgOfFunction((Function)a, (Variable)b)) {
                ret.first = b;
                ret.second = a;
                isOk = true;
            }
        } else if (tta == Type.FUNCTION && ttb == Type.FUNCTION) {
            // Проверяем, что функции имеют одинакове имя, рекурсивно проверяем внутренности функций
            Function af = (Function) a;
            Function bf = (Function) b;
            ret = functionCheck(af, bf);
            if (ret != null) {
                isOk = true;
            }
        } else if (tta == Type.CONSTANT && ttb == Type.CONSTANT) {
            // Константы заменять нельзя! Но если у нас одинаковые константы,
            // то необходимо уведомить дальнейшую часть алгоритма, что все ОК
            // при этом ни какой замены происходить не будет.
            if (a.equals(b)) {
                ret.first = a;
                ret.second = b;
                return ret; // не надо добавлять данную постановку в унификатор
            }
        }
        if (isOk) {
            m_unifier.add(ret);
            return ret;
        } else {
            return null;
        }

    }

    private static boolean variableDontArgOfFunction(Function f, Variable v) {
        for (Term t : f.args()) {
            Type ttype = getType(t);
            if (ttype == Type.VARIABLE) {
                if (t.equals(v)) {
                    return false;
                }
            } else if(ttype == Type.FUNCTION) {
                if (!variableDontArgOfFunction((Function)t, v)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void applySubstitution(Pair<Term, Term> sub, Predicate pred) {
        int numArgs = pred.args().size();
        Type subType = getType(sub.first);
        for (int i = 0; i < numArgs; i++) {
            Term t = pred.args().get(i);
            Type ttype = getType(t);

            if (ttype.equals(Type.VARIABLE)) {
                // Выполняем простую замену
                if (t.equals(sub.first)) {
                    pred.args().set(i, sub.second);
                }
            }else if (ttype.equals(Type.FUNCTION)) {
                // Сама функция не заменяется. Заменяется ее внутренности
                applySubstitutionForFunction(sub, (Function)t);
            }else if (ttype.equals(Type.CONSTANT)) {
                //  константу заменить нельзя!
            }

        }
    }

    private static void applySubstitutionForFunction(Pair<Term, Term> sub, Function f) {
        for (int i = 0; i < f.args().size(); i++) {
            Term t = f.args().get(i);
            Type type = getType(t);
            if (type == Type.FUNCTION) {
                applySubstitutionForFunction(sub, (Function) t);
            } else if (t.equals(sub.first)) {
                f.args().set(i, sub.second);
            }
        }
    }

    private static Type getType(Term t) {
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

    private  Pair<Term, Term> functionCheck(Function a, Function b) { // {Что заменяем, на что заменяем}
        if (a.name().equals(b.name())) { // Функции имеют одинаковые имена
            for (int i = 0; i < a.args().size(); i++) { // проверяем аргументы функции
                return findSubstitution(a.arg(i), b.arg(i));
            }
        }
        return null;
    }

}
