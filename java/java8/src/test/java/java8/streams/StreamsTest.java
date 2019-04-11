package java8.streams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StreamsTest {
	private List<String> list;

	@Before
	public void setUp() {
		this.list = Arrays.asList("ddd2", "aaa2", "bbb1", "aaa1", "bbb3", "ccc", "bbb2", "ddd1");
	}

	@Test
	public void testStreamsFilter() throws Exception {
		// Given

		// When
		System.out.println("Streams filter.");
		this.list.stream().filter(s -> s.startsWith("b")).forEach(System.out::println);
		System.out.println();

		// Then
	}

	@Test
	public void testStreamsSorted() throws Exception {
		// Given

		// When
		System.out.println("Streams sortered.");
		this.list.stream().sorted().filter(s -> s.startsWith("b")).forEach(System.out::println);
		System.out.println();

		// Then

	}

	/*
	 * Converts each element into another object.
	 */
	@Test
	public void testStreamsMap() throws Exception {
		// Given

		// When
		System.out.println("Streams Mapped.");
		this.list.stream().map(String::toUpperCase).sorted().filter(s -> s.startsWith("B"))
				.forEach(System.out::println);
		System.out.println();

		// Then

	}

	/*
	 * Matching operations to check whether a certain predicate matches the stream.
	 */
	@Test
	public void testStreamsMatch() throws Exception {
		// Given

		// When
		boolean anyStart = this.list.stream().anyMatch(s -> s.startsWith("a"));
		boolean allStart = this.list.stream().allMatch(s -> s.startsWith("a"));
		boolean noneStart = this.list.stream().noneMatch(s -> s.startsWith("z"));

		// Then
		assertThat(anyStart).isTrue();
		assertThat(allStart).isFalse();
		assertThat(noneStart).isTrue();
	}

	@Test
	public void testStreamsCount() throws Exception {
		// Given

		// When
		long count = this.list.stream().filter(s -> s.startsWith("a")).count();

		// Then
		assertThat(count).isEqualTo(2);
	}
}
