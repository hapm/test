package de.hapm.test;

import java.util.HashMap;
import java.util.Map.Entry;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * The ExceptionMatcher can be used to check if a {@link ThrowingContext} throws a given
 * exception for a given variable object. 
 * 
 * @author hapm
 *
 * @param <S> The type of the object, that changes in each execution of the {@link ThrowingContext}.
 * @param <T> The type of the {@link ThrowingContext}, this matcher can be used on.
 */
public class ExceptionMatcher<S, T extends ThrowingContext<S>> extends TypeSafeMatcher<T> {
	/**
	 * Saves the values to test on the {@link ThrowingContext}.
	 */
	private HashMap<S, Boolean> testValues = new HashMap<S, Boolean>();
	
	/**
	 * Saves the expected exception class.
	 */
	private Class<? extends Throwable> exceptionClass;
	
	/**
	 * Saves the throwable, that was thrown by the {@link ThrowingContext}.
	 */
	private Throwable lastThrowable;
	
	/**
	 * Saves if an exception was thrown or not, to show proper description.
	 */
	private boolean wrongBadValue;
	
	/**
	 * Saves the last value that was tested and didn't cause the expected behavior.
	 */
	private S lastVariable;
	
	/**
	 * Initializes a new instance of the ExceptionMatcher class, for the given 
	 * type of exception.
	 * 
	 * @param exception The class of the exception, that is expected to be thrown.
	 */
	public ExceptionMatcher(Class<? extends Throwable> exception) {
		this.exceptionClass = exception;
	}
	
	/**
	 * Describes what should happen.
	 * 
	 * @param desc The Description object to describe this matcher to.
	 */
	public void describeTo(Description desc) {
		desc.appendText(" throw ");
		desc.appendText(exceptionClass.getName());
		desc.appendText(" with variable ");
		desc.appendValue(lastVariable);
	}
	
	/**
	 * Describes what went wrong.
	 * 
	 * @param item The {@link ThrowingContext} that behaved not as expected.
	 * @param mismatchDescription The Description object to describe to, what went wrong.
	 */
	@Override
	protected void describeMismatchSafely(T item,
			Description mismatchDescription) {
		if (wrongBadValue) {
			mismatchDescription.appendText("throwed nothing");
		}
		else {
			mismatchDescription.appendText("throwed ");
			mismatchDescription.appendText(lastThrowable.getClass().getName());
		}
	}

	/**
	 * Runs the checks against the given {@link ThrowingContext}. 
	 * 
	 * @param context The context to run the tests on.
	 * @return Returns false if the ThrowingContext does not behave as expected, else true.
	 */
	@Override
	protected boolean matchesSafely(T context) {
		boolean correctInstance = true;
		wrongBadValue = false;
		for (Entry<S, Boolean> current : testValues.entrySet()) {
			lastVariable = current.getKey();
			try {
				context.run(current.getKey());
				wrongBadValue = current.getValue();
			}
			catch (Throwable ex) {
				lastThrowable = ex;
				correctInstance = exceptionClass.isInstance(ex);
			}
			
			if (!correctInstance || wrongBadValue) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Adds another value to the list of values that shouldn't cause the exception.
	 * 
	 * @param goodValue The value, that doesn't cause an exception.
	 * @return The matcher again, to be able to stack this calls.
	 */
	public ExceptionMatcher<S, T> andNotOn(S goodValue) {
		testValues.put(goodValue, false);
		return this;
	}


	/**
	 * Adds another value to the list of values that should cause the exception.
	 * 
	 * @param badValue The value, that does cause an exception.
	 * @return The matcher again, to be able to stack this calls.
	 */
	public ExceptionMatcher<S, T> andOn(S badValue) {
		testValues.put(badValue, true);
		return this;
	}
}
