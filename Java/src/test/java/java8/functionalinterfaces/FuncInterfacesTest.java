package java8.functionalinterfaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;

import org.junit.Test;

public class FuncInterfacesTest {
	/*
	 * Predicates -> boolean valued functions of one argument. Contains methods to
	 * compose complex logical terms (and, or, negate).
	 */
	@Test
	public void testPredicatesUsageWithLambdas() throws Exception {
		// Given
		Predicate<String> nonEmpty = s -> !s.isEmpty();

		// When
		boolean bol = nonEmpty.test("Foo");

		// Then
		assertThat(bol).isTrue();
	}

	@Test
	public void testPredicatesUsageMethodRef() throws Exception {
		// Given
		Predicate<String> isEmpty = String::isEmpty;
		Predicate<String> nonEmpty = isEmpty.negate();

		// When
		boolean bol = nonEmpty.test("Foo");

		// Then
		assertThat(bol).isTrue();
	}
}
