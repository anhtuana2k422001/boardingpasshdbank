package vn.com.hdbank.boardingpasshdbank.common.anotation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.jdbc.core.RowMapper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class MyModelRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;

    @Override
    public T mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        T model;
        try {
            model = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new SQLException("Failed to instantiate model class.", e);
        }
        setModelFields(rs, model);
        return model;
    }

    private void setModelFields(ResultSet rs, T model) throws SQLException {
        for (Field field : clazz.getDeclaredFields()) {
            MyColumn myColumn = field.getAnnotation(MyColumn.class);
            if (myColumn != null) {
                String columnName = StringUtils.defaultIfBlank(myColumn.name(), field.getName());
                String columnValue = rs.getString(columnName);
                if (columnValue == null && !myColumn.nullable()) {
                    throw new SQLException(StringUtils.join("Column ", columnName, " cannot be null."));
                }
                if (columnValue != null && columnValue.length() > myColumn.length()) {
                    throw new SQLException(StringUtils.join("Column ", columnName, " exceeded maximum length."));
                }
                try {
                    FieldUtils.writeField(model, field.getName(), parseColumnValue(columnValue, field.getType()), true);
                } catch (IllegalAccessException e) {
                    throw new SQLException("Failed to set column value.", e);
                }
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
            return Long.parseLong(columnValue);
        } else if (fieldType == Integer.class) {
            return Integer.parseInt(columnValue);
        } else if (fieldType == Double.class) {
            return Double.parseDouble(columnValue);
        } else if (fieldType == Boolean.class) {
            return Boolean.parseBoolean(columnValue);
        } else if (fieldType == LocalDate.class) {
            return LocalDate.parse(columnValue, DateTimeFormatter.ISO_LOCAL_DATE);
        } else if (fieldType == LocalTime.class) {
            return LocalTime.parse(columnValue, DateTimeFormatter.ISO_LOCAL_TIME);
        } else if (fieldType == LocalDateTime.class) {
            return LocalDateTime.parse(columnValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else {
            throw new SQLException("Unsupported column type.");
        }
    }
}
