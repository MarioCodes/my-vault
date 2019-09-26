package java8.lambdas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LambdasTest {
    private List<String> names;

    @Before
    public void setUp() {
        this.names = Arrays.asList("Peter", "Anna", "Mike", "Xenia");
    }

    @Test
    public void testSortingTraditionalWay() throws Exception {
        // When
        Collections.sort(this.names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        });

        // Then
        assertThat(this.names).isSorted();
    }

    @Test
    public void testSortingLambdasLongWay() throws Exception {
        // When
        Collections.sort(this.names, (String a, String b) -> {
            return a.compareTo(b);
        });

        // Then
        assertThat(this.names).isSorted();
    }

    @Test
    public void testSortingLambdasABitShorter() throws Exception {
        // When
        Collections.sort(this.names, (String a, String b) -> a.compareTo(b));

        // Then
        assertThat(this.names).isSorted();
    }

    @Test
    public void testSortingLambdasCorrectWay() throws Exception {
        // When
        Collections.sort(this.names, (a, b) -> a.compareTo(b));

        // Then
        assertThat(this.names).isSorted();
    }
}
