package lab4;

import java.util.*;

public class Function implements Term {
    private String m_sName; /// Навание функции
    private List<Term> m_ltArgs; /// Список аргументов функции

    public Function(String sName, List<String> lsVarNames) {
        m_sName = sName;
        m_ltArgs = new ArrayList<>(lsVarNames.size());
        for (String varName : lsVarNames) {
            m_ltArgs.add(new Variable(varName));
        };
    }

    public Function(String sName, Term ... vars) {
        m_sName = sName;
        m_ltArgs = new ArrayList<>(vars.length);
        m_ltArgs.addAll(Arrays.asList(vars));
    }

    private Function() {}


    @Override
    public String name() {
        return m_sName;
    }

    @Override
    public Term clone() {
        Function c = new Function();
        c.m_sName = m_sName;
        c.m_ltArgs = new ArrayList<>(m_ltArgs.size());
        for (Term t : m_ltArgs) {
            c.m_ltArgs.add(t.clone());
        }
        return c;
    }

    public Term arg(int index) {
        return m_ltArgs.get(index);
    }

    public Variable arg(String sName) {
        return null;
    }

    public List<Term> args() {
        return m_ltArgs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("function{")
            .append(m_sName)
            .append("; [");
        int len = m_ltArgs.size();
        Iterator<Term> itr = m_ltArgs.iterator();
        for (int i = 0; i < len-1; i++) {
            Term t = itr.next();
            sb.append(t.toString())
                    .append(", ");
        }
        Term t = itr.next();
        sb.append(t.toString())
                .append("]}");
        return sb.toString();
    }
}
