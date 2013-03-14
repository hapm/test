package de.hapm.test;

/**
 * The ThrowingContext describes the context, in which an exception should happen.
 * The context will be executed as often, as there where values provided to check.
 * 
 * @author hapm
 *
 * @param <T> The type of the objects, that change at every execution.
 */
public interface ThrowingContext<T> {
	/**
	 * This method is called for each value, that was provided to an {@link ExceptionMatcher}.
	 * 
	 * @param variant The object for the current execution.
	 * @throws Throwable There can be anything thrown in a {@link ThrowingContext}. The 
	 *                   {@link ExceptionMatcher} fetches it and check if it matches the given 
	 *                   exception class.
	 */
	void run(T variant) throws Throwable;
}
