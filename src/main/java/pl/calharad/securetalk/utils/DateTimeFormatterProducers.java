package pl.calharad.securetalk.utils;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import java.time.format.DateTimeFormatter;

@Dependent
public class DateTimeFormatterProducers {
    @Produces
    DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }
}
