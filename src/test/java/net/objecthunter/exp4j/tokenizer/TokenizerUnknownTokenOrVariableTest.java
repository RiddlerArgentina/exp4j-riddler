package net.objecthunter.exp4j.tokenizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This test is to check if {@link UnknownFunctionOrVariableException} generated
 * when expression contains unknown function or variable contains necessary
 * expected details.
 *
 * @author Bartosz Firyn (sarxos)
 */
public class TokenizerUnknownTokenOrVariableTest {

    @Test
    public void testTokenizationOfUnknownVariable() {
        final Tokenizer tokenizer = new Tokenizer("3 + x", null, null, null, true);
        Assertions.assertThrowsExactly(UnknownFunctionOrVariableException.class, () -> {
            while (tokenizer.hasNext()) {
                tokenizer.nextToken();
            }
        });
    }

    @Test
    public void testTokenizationOfUnknownVariable2() {
        try {
            final Tokenizer tokenizer = new Tokenizer("3 + x", null, null, null, true);
            while (tokenizer.hasNext()) {
                tokenizer.nextToken();
            }
            Assertions.fail("Exception expected");
        } catch (UnknownFunctionOrVariableException e) {
            String exp = "Unknown function or variable 'x' at pos 4 in expression '3 + x'";
            Assertions.assertEquals(exp, e.getMessage());
        }
    }

    @Test
    public void testTokenizationOfUnknownVariable1Details() {
        final Tokenizer tokenizer = new Tokenizer("3 + x", null, null, null, true);
        tokenizer.nextToken(); // 3
        tokenizer.nextToken(); // +

        try {
            tokenizer.nextToken(); // x
            Assertions.fail("Variable 'x' should be unknown!");
        } catch (UnknownFunctionOrVariableException e) {
            Assertions.assertEquals("x", e.getToken());
            Assertions.assertEquals(4, e.getPosition());
            Assertions.assertEquals("3 + x", e.getExpression());
        }
    }

    @Test
    public void testTokenizationOfUnknownVariable2Details() {
        final Tokenizer tokenizer = new Tokenizer("x + 3", null, null, null, true);

        try {
            tokenizer.nextToken(); // x
            Assertions.fail("Variable 'x' should be unknown!");
        } catch (UnknownFunctionOrVariableException e) {
            Assertions.assertEquals("x", e.getToken());
            Assertions.assertEquals(0, e.getPosition());
            Assertions.assertEquals("x + 3", e.getExpression());
        }
    }

    @Test
    public void testTokenizationOfUnknownFunction() {
        final Tokenizer tokenizer = new Tokenizer("3 + p(1)", null, null, null, true);
        Assertions.assertThrowsExactly(UnknownFunctionOrVariableException.class, () -> {
            while (tokenizer.hasNext()) {
                tokenizer.nextToken();
            }
        });
    }

    @Test
    public void testTokenizationOfUnknownFunction1Details() {

        final Tokenizer tokenizer = new Tokenizer("3 + p(1)", null, null, null, true);
        tokenizer.nextToken(); // 3
        tokenizer.nextToken(); // +

        try {
            tokenizer.nextToken(); // p
            Assertions.fail("Function 'p' should be unknown!");
        } catch (UnknownFunctionOrVariableException e) {
            Assertions.assertEquals("p", e.getToken());
            Assertions.assertEquals(4, e.getPosition());
            Assertions.assertEquals("3 + p(1)", e.getExpression());
        }
    }

    @Test
    public void testTokenizationOfUnknownFunction2Details() {

        final Tokenizer tokenizer = new Tokenizer("p(1) + 3", null, null, null, true);

        try {
            tokenizer.nextToken(); // p
            Assertions.fail("Function 'p' should be unknown!");
        } catch (UnknownFunctionOrVariableException e) {
            Assertions.assertEquals("p", e.getToken());
            Assertions.assertEquals(0, e.getPosition());
            Assertions.assertEquals("p(1) + 3", e.getExpression());
        }
    }
}
