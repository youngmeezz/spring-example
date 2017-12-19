package time;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author zacconding
 * @Date 2017-12-19
 * @GitHub : https://github.com/zacscoding
 */
public class LocalDateTimeTest {
    @Test
    public void of() {
        LocalDateTime t1 = LocalDateTime.of(2017, 12,10,0,0);
        LocalDateTime t2 = LocalDateTime.of(2017, 12,22,0,0);
        assertTrue(t1.getYear() == 2017);
        assertTrue(t1.getMonth() == Month.DECEMBER);
        assertTrue(t1.getDayOfMonth() == 10);
    }

    @Test
    public void parseFormat() {
        // format date and time
        LocalDateTime t1 = LocalDateTime.of(2017, 11,10,23,55);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        assertTrue(t1.format(formatter).equals("2017-11-10 23:55"));

        // parse date and time (must contain hour,minute??)
        String dateVal = "2017-12-20 00:00";
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime t2 = LocalDateTime.parse(dateVal,formatter);
        assertTrue(t2.format(formatter).equals(dateVal));
    }


    @Test(expected = DateTimeParseException.class)
    public void parseWithNoHourAndMinute() {
        String dateVal = "2017/12/20";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime t2 = LocalDateTime.parse(dateVal,formatter);
        System.out.println(t2.toString());
    }

    @Test
    public void plusMinus() {
        LocalDateTime t1 = LocalDateTime.of(2017, 11,10,23,55);
        assertTrue(t1.minusDays(5).getDayOfMonth() == 5);
        assertTrue(t1.plusDays(5).getDayOfMonth() == 15);
    }

    @Test
    public void is() {
        LocalDateTime t1 = LocalDateTime.of(2017, 12,10,0,0);
        LocalDateTime t2 = LocalDateTime.of(2017, 12,22,0,0);
        assertTrue(t1.isBefore(t2));
        assertFalse(t1.isAfter(t2));
    }

    @Test
    public void period() {
        LocalDateTime t1 = LocalDateTime.of(2015, 11,22,0,0);
        LocalDateTime t2 = LocalDateTime.of(2017, 7,10,0,0);

        Period period = Period.between(t1.toLocalDate(), t2.toLocalDate());
        assertFalse(period.isNegative());
        assertTrue(period.getYears() == 1);
        assertTrue(period.getMonths() == 7);
        assertTrue(period.getDays() == 18);

        Period reversePeriod = Period.between(t2.toLocalDate(), t1.toLocalDate());
        assertTrue(reversePeriod.isNegative());
        assertTrue(reversePeriod.getYears() == -1);
        assertTrue(reversePeriod.getMonths() == -7);
        assertTrue(reversePeriod.getDays() == -18);
    }
}
