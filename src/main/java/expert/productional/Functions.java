package expert.productional;

import java.util.Set;

public class Functions {
    public static int HashCode(Production prod) {
        int h = 101 * prod.getConclusion().hashCode() + 10101 * prod.getPremises().hashCode();
        return h;
    }

    public static boolean Equals(Production a, Production b) {
        Set<Term> ap = a.getPremises();
        Set<Term> bp = b.getPremises();
        Term at = a.getConclusion();
        Term bt = b.getConclusion();
        return a.getPremises().equals(b.getPremises()) && a.getConclusion().equals(b.getConclusion());
    }
}
