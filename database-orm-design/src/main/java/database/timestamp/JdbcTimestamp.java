package database.timestamp;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

// TODO. 处理时间戳在数据库中的存储
// JDBC driver will use default timezone (TimeZone.getDefault()) of JVM
// to transform timestamp before storing in DB
//
// 1. Change the TimeZone of the JVM which is very impacting (impacts other processes running in same JVM)
// 2. Override the JPA engine's timestamp setters in order not to use the default timezone
//    however this should be done at the JPA API level
public class JdbcTimestamp {

    // Calculate a shiftedTimestamp in order to get a correct UTC timestamp in the DB
    private static Date getShiftedUtcTimestamp() {
        TimeZone timezone = TimeZone.getDefault();
        int timezoneOffset = timezone.getRawOffset() + timezone.getDSTSavings(); // in ms

        Instant shiftedInstant = Instant.now(Clock.systemUTC()).minusMillis(timezoneOffset);
        return Date.from(shiftedInstant);
    }
}
