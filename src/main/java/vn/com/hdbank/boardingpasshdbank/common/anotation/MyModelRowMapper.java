package vn.com.hdbank.boardingpasshdbank.common.anotation;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class MyModelRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;
    private static final List<DateTimeFormatter> timestampFormatters = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnn"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnnnnn"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss+hh"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss-hh"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS+hh"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS-hh"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    );

    @Override
    public T mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        T model;
        try {
            model = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new SQLException("Failed to instantiate model class.", e);
        }
        setModelFields(rs, model);
        return model;
    }

    private void setModelFields(ResultSet rs, T model) throws SQLException {
        for (Field field : clazz.getDeclaredFields()) {
            MyColumn myColumn = field.getAnnotation(MyColumn.class);
            String columnName = (myColumn != null) ? StringUtils.defaultIfBlank(myColumn.name(), field.getName()) : field.getName();
            String columnValue = rs.getString(columnName);

            if (StringUtils.isEmpty(columnValue) && (myColumn == null || !myColumn.nullable())) {
                throw new SQLException(StringUtils.join("Column ", columnName, " cannot be null."));
            }
            if (StringUtils.isNotEmpty(columnValue) && myColumn != null && columnValue.length() > myColumn.length()) {
                throw new SQLException(StringUtils.join("Column ", columnName, " exceeded maximum length."));
            }
            try {
                FieldUtils.writeField(model, field.getName(), parseColumnValue(columnValue, field.getType()), true);
            } catch (IllegalAccessException e) {
                throw new SQLException("Failed to set column value.", e);
            }
        }
    }

    private Object parseColumnValue(String columnValue, Class<?> fieldType) throws SQLException {
        if (columnValue == null) {
            return null;
        }
        if (fieldType == String.class) {
            return columnValue;
        } else if (fieldType == Long.class) {
            return parseLong(columnValue);
        } else if (fieldType == Integer.class) {
            return parseInt(columnValue);
        } else if (fieldType == Double.class) {
            return parseDouble(columnValue);
        } else if (fieldType == BigDecimal.class) {
            return new BigDecimal(columnValue);
        } else if (fieldType == Boolean.class) {
            return parseBoolean(columnValue);
        } else if (fieldType == LocalDate.class) {
            return parseLocalDate(columnValue);
        } else if (fieldType == LocalTime.class) {
            return parseLocalTime(columnValue);
        } else if (fieldType == Timestamp.class) {
            return parseTimestamp(columnValue);
        } else if (fieldType == LocalDateTime.class) {
            return parseLocalDateTime(columnValue);
        } else if (fieldType == ZonedDateTime.class) {
            return parseZonedDateTime(columnValue);
        } else {
            throw new SQLException("Unsupported column type.");
        }
    }

    private Long parseLong(String s) {
        return Long.parseLong(s);
    }

    private Integer parseInt(String s) {
        return Integer.parseInt(s);
    }

    private Double parseDouble(String s) {
        return Double.parseDouble(s);
    }

    private Boolean parseBoolean(String s) {
        return Boolean.parseBoolean(s);
    }

    private LocalDate parseLocalDate(String s) {
        return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private LocalTime parseLocalTime(String s) {
        return LocalTime.parse(s, DateTimeFormatter.ISO_LOCAL_TIME);
    }

    private Timestamp parseTimestamp(String s) throws SQLException {
        try {
            return Timestamp.valueOf(s);
        } catch (IllegalArgumentException e) {
            try {
                LocalDateTime localDateTime = parseLocalDateTime(s);
                return Timestamp.valueOf(localDateTime);
            } catch (DateTimeParseException ignored) {
            }
            throw new SQLException("Unsupported timestamp format: " + s);
        }
    }

    private LocalDateTime parseLocalDateTime(String s) throws SQLException {
        try {
            return LocalDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME);

        } catch (DateTimeParseException e){
            for (DateTimeFormatter formatter : timestampFormatters) {
                try {
                    return LocalDateTime.parse(s, formatter);
                } catch (DateTimeParseException ignored) {
                }
            }
            throw new SQLException("Unsupported LocalDateTime format: " + s);
        }
    }

    private ZonedDateTime parseZonedDateTime(String s) {
        return ZonedDateTime.parse(s, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
}
