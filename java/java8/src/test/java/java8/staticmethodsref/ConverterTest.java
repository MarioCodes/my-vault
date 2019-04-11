package java8.staticmethodsref;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ConverterTest {
	private Converter<String, Integer> converter;

	@Test
	public void testImplementationWithLambdas() throws Exception {
		// Given
		this.converter = (from) -> Integer.valueOf(from);

		// When
		Integer converted = this.converter.convert("123");

		// Then
		assertThat(converted).isEqualTo(123);
	}

	@Test
	public void testImplementationStaticMethodReference() throws Exception {
		// Given
		this.converter = Integer::valueOf;

		// When
		int converted = this.converter.convert("123");

		// Then
		assertThat(converted).isEqualTo(123);
	}
}
