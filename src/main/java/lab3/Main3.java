package lab3;

import expert.alg.Pair;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Main3 {
    public static void main(String[] args) {
        List<Term> CNF = example1();
        print(CNF);
        System.out.println("");
        Pair<Boolean, Set<Term>> res = ResolutionMethod.solve(CNF);
        if (res.first) {
            System.out.println("Нулевой дизъюнкт был построен!");
        } else {
            System.out.println("Нулевой дизъюнкт не был найден");
        }

        System.out.println("\nСписок всех теромов:");
        for(Term t : res.second) {
            System.out.println(t);
        }


    }

    /**
     * A+B+C; A+-(B); A+-(C); -(A)
     * @return
     */
    static List<Term> example1() {
        Term A = new Term("A");
        Term B = new Term("B");
        Term C = new Term("C");

        Term V1 = A.or(B).or(C);
        Term V2 = A.or(B.not());
        Term V3 = A.or(C.not());
        Term V4 = A.not();
        return Arrays.asList(V1, V2, V3, V4);
    }

    /**
     * V1 = A+-(B)+-(C);
     * V2 = -(A)+-(B)+-(C);
     * V3 = A+B+-(C)+D;
     * V4 = A+D;
     * V5 = A+C;
     * V6 = A+B+D
     * @return
     */
    static List<Term> example2() {
        Term A = new Term("A");
        Term B = new Term("B");
        Term C = new Term("C");
        Term D = new Term("D");

        Term V1 = A.or(B.not()).or(C.not());
        Term V2 = A.not().or(B.not()).or(C.not());
        Term V3 = A.or(B).or(C.not()).or(D);
        Term V4 = A.or(D);
        Term V5 = A.or(C);
        Term V6 = A.or(B).or(D);
        return Arrays.asList(V1, V2, V3, V4, V5, V6);
    }

    static void print(List<Term> terms) {
        System.out.println("Конъюнктивная нормальная форма:");
        StringBuilder sb = new StringBuilder();
        Iterator<Term> itr = terms.iterator();
        int len = terms.size();
        for (int i = 0; i < len-1; i++) {
            Term t = itr.next();
            sb.append("(")
                    .append(t.toString())
                    .append(")")
                    .append("*");
        }
        Term last = itr.next();
        sb.append("(")
                .append(last.toString())
                .append(")");
        System.out.println(sb);
    }
}
