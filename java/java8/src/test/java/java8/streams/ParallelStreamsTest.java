package java8.streams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/*
 * They're the same, but the parallel are multithread so they're much faster.
 */
public class ParallelStreamsTest {
	private List<String> list;

	@Before
	public void setUp() {
		this.list = Arrays.asList("ddd2", "aaa2", "bbb1", "aaa1", "bbb3", "ccc", "bbb2", "ddd1");
	}

	@Test
	public void testParallelStreams() throws Exception {
		// Given

		// When
		long count = this.list.parallelStream().sorted().filter(s -> s.startsWith("a")).count();

		// Then
		assertThat(count).isEqualTo(2);
	}

}
