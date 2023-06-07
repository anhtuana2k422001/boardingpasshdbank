package vn.com.hdbank.boardingpasshdbank.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class DateUtils {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static Date parseDate(String dateString, String dateFormat) {
        try {
            SimpleDateFormat inputDateFormat  = new SimpleDateFormat(dateFormat);
            return inputDateFormat .parse(dateString);
        }catch (Exception e) {
            LOGGER.info("Failed to parse date");
           return null;
        }
    }

    public static Date parseDate(String dateString) {
        return parseDate(dateString, DEFAULT_DATE_FORMAT);
    }

    public static Timestamp parseTimestamp(String timestampString, String timestampFormat) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timestampFormat);
            Date date = dateFormat.parse(timestampString);
            return new Timestamp(date.getTime());
        } catch (Exception e) {
            LOGGER.info("Failed to parse timestamp");
            return null;
        }
    }

    public static Timestamp parseTimestampSave(String timestampString) {
        return parseTimestamp(timestampString, DEFAULT_TIMESTAMP_FORMAT);
    }
}
