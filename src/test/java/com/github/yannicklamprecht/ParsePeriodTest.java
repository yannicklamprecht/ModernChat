import org.joda.time.Period;
import org.joda.time.format.PeriodFormatterBuilder;
import org.junit.jupiter.api.Test;

public class ParsePeriodTest {

    @Test
    public void testPeriod() {
        var parser = new PeriodFormatterBuilder()
                .appendDays().appendSuffix("d").appendSeparatorIfFieldsAfter(" ")
                .appendHours().appendSuffix("h").appendSeparatorIfFieldsAfter(" ")
                .appendMinutes().appendSuffix("min")
                .toFormatter();

        Period period = parser.parsePeriod("2d 5h 30min");
        System.out.println(period.toStandardDuration());
    }

}
