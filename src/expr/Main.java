package expr;

public class Main {
    public static void main(String[] args) {
        Expression expr = ExpressionFactory.parse("TRUE & FALSE & TRUE");
        System.out.println(expr + " = " + expr.evaluate());
    }
}
