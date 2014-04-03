package expr;

import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class ExpressionFactory {
    
    /**
     * @param input string representing a conjunctive boolean expression
     * @return Expression corresponding to the input
     */
    public static Expression parse(String input) {
        // Create a stream of tokens using the lexer.
        CharStream stream = new ANTLRInputStream(input);
        ExpressionLexer lexer = new ExpressionLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);
        
        // Feed the tokens into the parser.
        ExpressionParser parser = new ExpressionParser(tokens);
        parser.reportErrorsAsExceptions();
        
        // Generate the parse tree using the starter rule.
        ParseTree tree = parser.expression(); // "expression" is the starter rule
        
        // debugging option #1: print the tree to the console
//        System.err.println(tree.toStringTree(parser));

        // debugging option #2: show the tree in a window
//        ((RuleContext)tree).inspect(parser);

        // debugging option #3: walk the tree with a listener
//        new ParseTreeWalker().walk(new PrintEverythingListener(), tree);
        
        // Finally, construct an Expression value by walking over the parse tree.
        ParseTreeWalker walker = new ParseTreeWalker();
        ExpressionCreatorListener listener = new ExpressionCreatorListener();
        walker.walk(listener, tree);
        
        // return the Expression value that the listener created
        return listener.getExpression();
    }
    
    private static class ExpressionCreatorListener extends ExpressionBaseListener {
        private Stack<Expression> stack = new Stack<>();
        
        @Override
        public void exitLiteral(ExpressionParser.LiteralContext ctx) {
            Expression literal = new BooleanLiteral(ctx.start.getType() == ExpressionLexer.TRUE);
            stack.push(literal);
        }
        
        @Override
        public void exitConjunction(ExpressionParser.ConjunctionContext ctx) {
            if (ctx.AND() != null) {
                // we matched the AND rule
                Expression right = stack.pop();
                Expression left = stack.pop();
                Expression and = new And(left, right);
                stack.push(and);
            } else {
                // do nothing, because we just matched a literal and its BooleanLiteral is already on the stack
            }
        }
        
        @Override
        public void exitExpression(ExpressionParser.ExpressionContext ctx) {
            // do nothing, because the top of the stack should have the node already in it
            assert stack.size() == 1;
        }
        
        public Expression getExpression() {
            return stack.get(0);
        }
    }
    

    static class PrintEverythingListener extends ExpressionBaseListener {
        public void enterExpression(ExpressionParser.ExpressionContext ctx) { System.err.println("entering expression " + ctx.getText()); }
        public void exitExpression(ExpressionParser.ExpressionContext ctx) { System.err.println("exiting expression " + ctx.getText()); }

        public void enterConjunction(ExpressionParser.ConjunctionContext ctx) { System.err.println("entering conjunction " + ctx.getText()); }
        public void exitConjunction(ExpressionParser.ConjunctionContext ctx) { System.err.println("exiting conjunction " + ctx.getText()); }

        public void enterLiteral(ExpressionParser.LiteralContext ctx) { System.err.println("entering literal " + ctx.getText()); }
        public void exitLiteral(ExpressionParser.LiteralContext ctx) { System.err.println("exiting literal " + ctx.getText()); }
    }

}
