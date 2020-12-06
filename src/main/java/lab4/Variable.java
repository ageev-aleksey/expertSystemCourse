package lab4;

import javax.management.ValueExp;

public class Variable implements Term {
    private String m_sName;
    private Term m_tValue;

    public Variable(String sName) {
        m_sName = sName;
        m_tValue = null;
    }

    public Variable(String sName, Term tValue) {
        m_sName = sName;
        m_tValue = tValue;
    }

    @Override
    public String name() {
        return m_sName;
    }


    public void value(Term tValue) {
        m_tValue = tValue;
    }

    public Term value() {
        return m_tValue;
    }

    @Override
    public String toString() {
        if (m_tValue != null) {
            return "var{" + m_sName + "; " + m_tValue.toString() +  "}";
        } else {
            return "var{" + m_sName + "; (NULL)}";
        }

    }
}
