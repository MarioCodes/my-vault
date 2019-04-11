package java8.staticmethodsref;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SomethingTest {
	private Something something;

	private Converter<String, String> converter;

	@Before
	public void setUp() {
		this.something = new Something();
	}

	@Test
	public void testMethodStaticReference() throws Exception {
		// Given
		this.converter = this.something::startsWith;

		// When
		String converted = this.converter.convert("Java");

		// Then
		assertThat(converted).isEqualTo("J");
	}
}
