package de.hapm.test;

public interface ThrowingContext<T> {
	void run(T variant) throws Throwable;
}
