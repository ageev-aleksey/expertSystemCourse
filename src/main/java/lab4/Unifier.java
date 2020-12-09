package lab4;

import expert.alg.Pair;

import java.util.List;

public class Unifier {
    List<Pair<Term, Term>> m_unifier;
    public Unifier(List<Pair<Term, Term>> unifier) {
        m_unifier = unifier;
    }

    public Predicate apply(Predicate p) {
        return Unif.applyUnifier(p, m_unifier);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Unifier{");
        for (Pair<Term, Term> sub : m_unifier) {
            sb.append("(")
                    .append(sub.first)
                    .append("; ")
                    .append(sub.second)
                    .append("), ");
        }
        sb.append("}");
        return sb.toString();
    }
}
