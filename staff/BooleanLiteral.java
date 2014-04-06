package expr;

public class BooleanLiteral implements Expression {
    
    private boolean value;
    
    public BooleanLiteral(boolean value) {
        this.value = value;
    }
    
    public boolean evaluate() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return "Literal(" + value + ")";
    }
}
