package es.msanchez.templates.java.spring.builder.generic;

import es.msanchez.templates.java.spring.entity.Person;
import org.assertj.core.api.BDDAssertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

public class RandomizerTest {

    private Randomizer<Person> randomizer = new Randomizer<>();

    @Test
    public void testName() {
        // @GIVEN
        final Person bean = new Person();

        // @WHEN
        this.randomizer.fill(bean);

        // @THEN
        BDDAssertions.assertThat(bean).isNotNull();
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(bean.getName()).hasSize(50);
            soft.assertThat(bean.getAge()).isInstanceOf(Integer.class).isPositive();
            soft.assertThat(bean.getId()).isInstanceOf(Long.class).isPositive();
            soft.assertThat(bean.getSmoker()).isInstanceOf(Boolean.class);
        });
    }

}