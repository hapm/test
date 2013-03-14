package de.hapm.test;

public class Matchers {

	public static <T, S extends ThrowingContext<T>> ExceptionMatcher<T, S> throwsEx(
			Class<? extends Throwable> exception, T badValue) {
		return new ExceptionMatcher<T, S>(exception).andOn(badValue);
	}
}
