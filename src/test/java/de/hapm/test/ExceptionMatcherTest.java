package de.hapm.test;

import static org.junit.Assert.*;
import static de.hapm.test.Matchers.*;
import org.junit.Test;
import de.hapm.test.ExceptionMatcher;
import de.hapm.test.ThrowingContext;

public class ExceptionMatcherTest {

	@Test
	public void test() {
		String badValue = null;
		ExceptionMatcher<String, ThrowingContext<String>> matcher = throwsEx(NullPointerException.class, badValue).andNotOn("notBad");
		
		ThrowingContext<String> context = new ThrowingContext<String>() {
			public void run(String variant) throws Throwable {
				variant = variant.toLowerCase();
			}
		};
		
		assertThat(context, matcher);
	}

}
