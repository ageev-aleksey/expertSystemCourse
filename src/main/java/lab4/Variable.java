package lab4;

import javax.management.ValueExp;

public class Variable implements Term {
    private String m_sName;
//    private Term m_tValue;

    public Variable(String sName) {
        m_sName = sName;
//        m_tValue = null;
    }

//    public Variable(String sName, Term tValue) {
//        m_sName = sName;
//        m_tValue = tValue;
//    }

    private Variable() {}

    @Override
    public String name() {
        return m_sName;
    }

    @Override
    public Term clone() {
        Variable c = new Variable();
        c.m_sName = m_sName;
        return c;
    }

//
//    public void value(Term tValue) {
//        m_tValue = tValue;
//    }
//
//    public Term value() {
//        return m_tValue;
//    }

    @Override
    public String toString() {
        return "var{" + m_sName + "}";
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
        Variable other = (Variable) obj;
        return m_sName.equals(other.m_sName);
    }
}
