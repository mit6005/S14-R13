package expr;

/**
 * Represents a boolean expression.
 * 
 * Expression = BooleanLiteral(value:boolean) + And(left,right:Expression)
 */
public interface Expression {
    
    /**
     * @return the value of this expression
     */
    public boolean evaluate();
    
}
