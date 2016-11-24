package net.objecthunter.exp4j.tokenizer;

/**
 * This exception is being thrown whenever {@link Tokenizer} finds unknown function or variable.
 *
 * @author Bartosz Firyn (sarxos)
 */
public class UnknownFunctionOrVariableException extends IllegalArgumentException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private final String message;
	private final String expression;
	private final String token;
	private final int position;

	public UnknownFunctionOrVariableException(String exp, int position, int length) {
		this.expression = exp;
		this.token = token(exp, position, length);
		this.position = position;
		this.message = String.format(
            "Unknown function or variable '%s' at pos %d in expression '%s'", token, position, exp
        );
	}

	private static String token(String expression, int position, int length) {

		int len = expression.length();
		int end = position + length - 1;

		if (len < end) {
			end = len;
		}

		return expression.substring(position, end);
	}

	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * @return Expression which contains unknown function or variable
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @return The name of unknown function or variable
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @return The position of unknown function or variable
	 */
	public int getPosition() {
		return position;
	}
}
