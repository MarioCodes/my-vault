package java8.datetime;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeMiscTests {

    @Test
    public void testLocalDate() {
        // Given
        final LocalDate localDate1 = LocalDate.of(2015, 02, 20);
        final LocalDate localDate2 = LocalDate.parse("2015-02-20");

        final LocalDate tomorrow1 = LocalDate.now().plusDays(1);
        final LocalDate future = LocalDate.now().plus(1, ChronoUnit.CENTURIES);

        final DayOfWeek dow = LocalDate.parse("2015-02-20").getDayOfWeek();
        final boolean leapYear = LocalDate.parse("2015-02-20").isLeapYear();

        final boolean isNotBefore = localDate1.isBefore(localDate2);
        final boolean isNotAfter = localDate1.isAfter(localDate2);

        // When

        // Then
        BDDAssertions.assertThat(localDate1).isEqualTo(localDate2);
        BDDAssertions.assertThat(tomorrow1).isEqualTo(future);
    }

    @Test
    public void testLocalTime() {
        // Given
        final LocalTime localTime1 = LocalTime.now();
        final LocalTime localTime2 = LocalTime.parse("06:30");
        final LocalTime localTime3 = LocalTime.of(6, 30);

        final LocalTime future = LocalTime.now().plusHours(1);
        final LocalTime max = LocalTime.MAX;

        // When

        // Then
    }

    @Test
    public void testLocalDateTime() {
        // Given
        final LocalDateTime ldt1 = LocalDateTime.now();
        final LocalDateTime ldt2 = LocalDateTime.of(2015, Month.FEBRUARY, 20, 06, 30);
        final LocalDateTime ldt3 = LocalDateTime.parse("2015-02-20T06:30:00");

        final LocalDateTime future1 = ldt1.plusDays(1);
        final LocalDateTime future2 = ldt1.plusHours(5);

        // When

        // Then

    }

    @Test
    public void testZonedDateTime() {
        // Given
        final LocalDateTime ldt1 = LocalDateTime.now();
        final ZoneId zoneId = ZoneId.of("Europe/Berlin");

        final ZonedDateTime zdt1 = ZonedDateTime.of(ldt1, zoneId);
        final ZonedDateTime zdt2 = ZonedDateTime.parse("2015-05-28T10:30:15+01:00[Europe/Paris]");


        final ZoneOffset offset = ZoneOffset.of("+02:00");
        final OffsetDateTime odt1 = OffsetDateTime.of(ldt1, offset);

        // When

        // Then

    }

    @Test
    public void testPeriodAndDuration() {
        // Given
        final LocalDate ldt1 = LocalDate.now();
        final LocalDate ldt2 = ldt1.plusDays(5);

        final Period period = Period.between(ldt1, ldt2);
        final int days = period.getDays();

        final LocalTime lt1 = LocalTime.now();
        final LocalTime lt2 = lt1.plusMinutes(50);

        final Duration duration = Duration.between(lt1, lt2);
        final int nanoseconds = duration.getNano();

        // When

        // Then

    }

}