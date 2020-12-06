package lab4;

public class Main4 {
    public static void main(String[] args) {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable z = new Variable("z");

        Function f = new Function("f", x);
        z.value(f);
        Function g = new Function("g", z, y);

        System.out.println(x.toString());
        System.out.println(y.toString());
        System.out.println(z.toString());

        System.out.println(f.toString());
        System.out.println(g.toString());
    }
}
