package vn.com.hdbank.boardingpasshdbank.common.anotation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyModelRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;

    public MyModelRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T model;
        try {
            model = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new SQLException("Failed to instantiate model class.", e);
        }
        for (Field field : clazz.getDeclaredFields()) {
            MyColumn myColumn = field.getAnnotation(MyColumn.class);
            if (myColumn != null) {
                String columnName = myColumn.name();
                if (StringUtils.isEmpty(columnName)) {
                    columnName = field.getName();

                }
                String columnValue = rs.getString(columnName);
                if (columnValue == null && !myColumn.nullable()) {
                    throw new SQLException(StringUtils.join("Column ", columnName , " cannot be null.") );
                }
                if (columnValue != null && columnValue.length() > myColumn.length()) {
                    throw new SQLException(StringUtils.join("Column " , columnName , " exceeded maximum length."));
                }
                field.setAccessible(true);
                try {
                    if (field.getType() == String.class) {
                        field.set(model, columnValue);
                    } else if (field.getType() == Long.class) {
                        field.set(model, Long.parseLong(columnValue));
                    } else {
                        throw new SQLException("Unsupported column type.");
                    }
                } catch (IllegalAccessException e) {
                    throw new SQLException("Failed to set column value.", e);
                }
            }
        }
        return model;
    }
}
