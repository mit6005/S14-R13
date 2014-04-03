package expr;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExpressionFactoryTest {

    @Test
    public void testLiterals() {
        assertTrue(ExpressionFactory.parse("TRUE").evaluate());
        assertFalse(ExpressionFactory.parse("   FALSE    ").evaluate());
    }

    @Test
    public void testAnd() {
        assertTrue(ExpressionFactory.parse("TRUE &TRUE").evaluate());
        assertFalse(ExpressionFactory.parse("TRUE & TRUE & FALSE").evaluate());
    }

    @Test
    public void testOtherLiterals() {
        assertFalse(ExpressionFactory.parse("T & true & false & true").evaluate());
    }

    @Test
    public void testNot() {
        assertFalse(ExpressionFactory.parse("!TRUE").evaluate());
        assertTrue(ExpressionFactory.parse("!FALSE").evaluate());
    }

}
