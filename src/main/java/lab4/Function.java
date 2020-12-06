package lab4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Function implements Term {
    private String m_sName; /// Навание функции
    private List<Variable> m_ltArgs; /// Список аргументов функции

    public Function(String sName, List<String> lsVarNames) {
        m_sName = sName;
        m_ltArgs = new ArrayList<>(lsVarNames.size());
        for (String varName : lsVarNames) {
            m_ltArgs.add(new Variable(varName));
        };
    }

    public Function(String sName, Variable ... vars) {
        m_sName = sName;
        m_ltArgs = new ArrayList<>(vars.length);
        for (int i = 0; i < vars.length; i++) {
            m_ltArgs.add(vars[i]);
        }
    }


    @Override
    public String name() {
        return null;
    }

    public Variable arg(int index) {
        return m_ltArgs.get(index);
    }

    public Variable arg(String sName) {
        return null;
    }

    public List<Variable> args() {
        return m_ltArgs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("function{")
            .append(m_sName)
            .append("; [");
        int len = m_ltArgs.size();
        Iterator<Variable> itr = m_ltArgs.iterator();
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
