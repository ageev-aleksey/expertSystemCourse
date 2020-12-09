package lab4;

import expert.alg.Pair;

import java.util.List;

public class Main4 {
    public static void main(String[] args) {
//        Variable x = new Variable("x");
//        Variable y = new Variable("y");
//        Variable z = new Variable("z");
//
//        Function f = new Function("f", x);
//        z.value(f);
//        Function g = new Function("g", z, y);
//
//        System.out.println(x.toString());
//        System.out.println(y.toString());
//        System.out.println(z.toString());
//
//        System.out.println(f.toString());
//        System.out.println(g.toString());

        Pair<Predicate, Predicate> pp = example3();
        System.out.println(pp.first);
        System.out.println(pp.second);
        Unif unif = new Unif();
        Pair<Predicate, Unifier> sub = unif.apply(pp.first, pp.second);
        if (sub != null) {
            System.out.println("\nAfter unif: " + sub.second);
            System.out.println(" -- " + sub.first);
        } else {
            System.out.println("Предикаты не унифицируются");
        }

    }

    /**
     * P(x, a)
     * P(c, y)
     * @return
     */
    static Pair<Predicate, Predicate> example1() {
        Variable x = new Variable("x");
        Constant a = new Constant("a");
        Constant c  = new Constant("c");
        Variable y = new Variable("y");

        Pair<Predicate, Predicate> ret = new Pair<>();
        ret.first =  new Predicate("P", x, a);
        ret.second = new Predicate("P", c, y);
        return ret;
    }

    /**
     * P(f(g(x))
     * P(y)
     * @return
     */
    static Pair<Predicate, Predicate> example2() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");

        Function g = new Function("g", x);
        Function f = new Function("f", g);

        Pair<Predicate, Predicate> ret = new Pair<>();
        ret.first =  new Predicate("P", f);
        ret.second = new Predicate("P", y);
        return ret;
    }

    /**
     * P(x, x)
     * P(a, y)
     * @return
     */
    static Pair<Predicate, Predicate> example3() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Constant a = new Constant("a");

        Function f1 = new Function("f", x);
        Function f2 = new Function("f", a);

        Pair<Predicate, Predicate> ret = new Pair<>();
        ret.first =  new Predicate("P", f1, x);
        ret.second = new Predicate("P", f2, y);
        return ret;
    }

    /**
     * P(a, x, f(g(y)))
     * P(z, f(x), f(u))
     * @return
     */
    static Pair<Predicate, Predicate> example4() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable z = new Variable("z");
        Variable u = new Variable("u");

        Constant a = new Constant("a");

        Function g = new Function("g", y);
        Function f1 = new Function("f", g);
        Function f2 = new Function("f", z);
        Function f3 = new Function("f", u);

        Pair<Predicate, Predicate> ret = new Pair<>();
        ret.first =  new Predicate("P", a, x, f1);
        ret.second = new Predicate("P", z, f2, f3);
        return ret;
    }

    /**
     * P(x, a)
     * P(y, b)
     * @return
     */
    static Pair<Predicate, Predicate> example5() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");

        Constant a = new Constant("a");
        Constant b = new Constant("b");

        Pair<Predicate, Predicate> ret = new Pair<>();
        ret.first = new Predicate("P", x, a);
        ret.second = new Predicate("P", y, b);
        return ret;
    }

    /**
     * P(x, f(x, g(x)))
     * P(a, f(u, g(y)))
     * @return
     */
    static Pair<Predicate, Predicate> example6() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable u = new Variable("u");

        Constant a = new Constant("a");

        Function g1 = new Function("g", x);
        Function g2 = new Function("g", y);
        Function f1 = new Function("f", x, g1);
        Function f2 = new Function("f", u, g2);

        Pair<Predicate, Predicate> ret = new Pair<>();
        ret.first = new Predicate("P", x, f1);
        ret.second = new Predicate("P", a, f2);
        return ret;
    }


}
