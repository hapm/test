package de.hapm.test;

import java.util.HashMap;
import java.util.Map.Entry;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ExceptionMatcher<S, T extends ThrowingContext<S>> extends TypeSafeMatcher<T> {
	private HashMap<S, Boolean> testValues = new HashMap<S, Boolean>();
	private Class<? extends Throwable> exceptionClass;
	private Throwable lastThrowable;
	private boolean correctInstance;
	private boolean wrongBadValue;
	private S lastVariable;
	
	public ExceptionMatcher(Class<? extends Throwable> exception) {
		this.exceptionClass = exception;
	}
	
	public void describeTo(Description desc) {
		desc.appendText(" throw ");
		desc.appendText(exceptionClass.getName());
		desc.appendText(" with variable ");
		desc.appendValue(lastVariable);
	}
	
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
		//super.describeMismatchSafely(item, mismatchDescription);
	}

	@Override
	protected boolean matchesSafely(T context) {
		correctInstance = true;
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

	public ExceptionMatcher<S, T> andNotOn(S goodValue) {
		testValues.put(goodValue, false);
		return this;
	}

	public ExceptionMatcher<S, T> andOn(S badValue) {
		testValues.put(badValue, true);
		return this;
	}
}
