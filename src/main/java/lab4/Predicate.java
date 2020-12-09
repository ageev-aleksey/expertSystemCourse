package lab4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Predicate implements Cloneable{
    private String m_sName;
    private List<Term> m_ltArgs;

    public Predicate(String name, Term ... vars) {
        m_ltArgs = new ArrayList<>(vars.length);
        m_ltArgs.addAll(Arrays.asList(vars));
        m_sName = name;
    }

    private Predicate(){}

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

    public Predicate clone() {
        Predicate c = new Predicate();
        c.m_sName = m_sName;
        c.m_ltArgs = new ArrayList<>(m_ltArgs.size());
        for (Term t : m_ltArgs) {
            c.m_ltArgs.add(t.clone());
        }
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Predicate p = (Predicate) obj;
        return m_sName.equals(p.m_sName);

    }
}
