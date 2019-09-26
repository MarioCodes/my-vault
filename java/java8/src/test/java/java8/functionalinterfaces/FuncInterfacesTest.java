package java8.functionalinterfaces;

import org.junit.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class FuncInterfacesTest {

	/*
     * Predicates -> boolean valued functions of one argument. Contains methods to
     * compose complex logical terms (and, or, negate).
     */
    @Test
    public void testPredicatesUsageWithLambdas() throws Exception {
        // Given
        final Predicate<String> nonEmpty = s -> !s.isEmpty();

        // When
		final boolean bol = nonEmpty.test("Foo");

        // Then
        assertThat(bol).isTrue();
    }

    @Test
    public void testPredicatesUsageMethodRef() throws Exception {
        // Given
		final Predicate<String> isEmpty = String::isEmpty;
		final Predicate<String> nonEmpty = isEmpty.negate();

        // When
		final boolean bol = nonEmpty.test("Foo");

        // Then
        assertThat(bol).isTrue();
    }
}
