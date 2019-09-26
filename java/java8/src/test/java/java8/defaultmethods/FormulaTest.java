package java8.defaultmethods;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FormulaTest {

	private Formula formula;

    @Test
    public void testFormulaUsageWithAnonClass() {
        // Given
        double result1, result2;

        this.formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };

        // When
        result1 = this.formula.calculate(8);
        result2 = this.formula.sqrt(16);

        // Then
        assertThat(result1).isNotZero();
        assertThat(result2).isEqualTo(4);
    }
}
