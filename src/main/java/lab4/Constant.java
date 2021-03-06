package lab4;

public class Constant implements Term {
    private String m_sName;

    public Constant(String sName) {
        m_sName = sName;
    }



    @Override
    public String name() {
        return m_sName;
    }

    @Override
    public Term clone() {
        return new Constant(m_sName);
    }

    @Override
    public String toString() {
        return "const{" + m_sName + "}";
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
        Constant other = (Constant) obj;
        return m_sName.equals(other.m_sName);
    }
}
