package expr;

public class And implements Expression {
    
    Expression left;
    Expression right;
    
    public And(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    public boolean evaluate() {
        return left.evaluate() && right.evaluate();
    }
    
    @Override
    public String toString() {
        return "And(" + left + "," + right + ")";
    }
}
