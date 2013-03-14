Simple JUnit test extensions
============================
This little package adds a matcher for exceptions to JUnits matcher catalog. I tried to make it as 
easy as possible, so you don't need the throw/catch/fail() structure anymore, and get quite well 
formated information about what went wrong, as you know it from other matchers.

## Installation

For now, there is no maven repository hosting this. If it get something bigger than what it is 
actually, I'll see if I publish it somewhere. For now you need to check it out, compile it, and 
install it to your local maven artifact repository.

## Usage

First of all, reference the package from your current project, by adding the package to your 
classpath for JUnit executions. How to do that, depends on the IDE you are working with. If you 
have maven support in your project, you can easily add the following to your poms dependencies 
section (be sure to change the version to what you want it to be, actually there is only 
0.0.1-SNAPSHOT so):

```xml
		<dependency>
			<groupId>de.hapm</groupId>
			<artifactId>test</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<scope>test</scope>
		</dependency>
```

After the reference is set, you can use the matchers in this package. Currently there is only one 
matcher in this package, the ExceptionMatcher. The Matcher class provides a static method you can 
use to create an ExceptionMatcher.

### ExceptionMatcher

The ExceptionMatcher is used to test your code for throwing expected Exceptions for given values. To
create one, you can use the [Matchers.throwsEx(...)](src/main/java/de/hapm/test/Matchers.java#L13) 
method. Here is a little example of how it can look like:

```java
import static org.junit.Assert.*;
import static de.hapm.test.Matchers.*;
import org.junit.Test;
import de.hapm.test.ExceptionMatcher;
import de.hapm.test.ThrowingContext;

public class ExceptionMatcherTest {

	@Test
	public void test() {
		String badValue = null;
		String goodValue = "notBad";
		ThrowingContext<String> context = ;
		
		assertThat(new ThrowingContext<String>() {
			public void run(String variant) throws Throwable {
				variant = variant.toLowerCase();
			}
		}, throwsEx(NullPointerException.class, badValue).andNotOn(goodValue));
	}

}
```

## License

This code is distributed under v3 of the [Lether Gerneral Public License](LICENSE.md)