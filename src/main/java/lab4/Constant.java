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
    public String toString() {
        return "const{" + m_sName + "}";
    }
}
