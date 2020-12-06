package lab4;

import java.util.Iterator;
import java.util.List;

public class Predicate {
    private String m_sName;
    private List<Term> m_ltArgs;

    public String name() {
        return m_sName;
    }


    public List<Term> args() {
        return m_ltArgs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("predicate{")
                .append(m_sName)
                .append("; ");
        int len = m_ltArgs.size();
        Iterator<Term> itr = m_ltArgs.iterator();
        for (int i = 0; i < len-1; i++) {
            Term t = itr.next();
            sb.append(t.toString())
                    .append("; ");
        }
        Term t = itr.next();
        sb.append(t.toString())
                .append("}");
        return sb.toString();
    }
}
