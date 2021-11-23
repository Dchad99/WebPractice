package com.store.repositories.db_config.queries;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.join;
import static java.lang.String.valueOf;

@Slf4j
public class SqlQueryGenerator implements QueryGenerator {

    @Override
    public String findAll(Class<?> clazz) {
        return String.format(QueryTemplates.SELECT_ALL.get(), getTableName(clazz));
    }

    @Override
    public String findById(Object idValue, Class<?> clazz) {
        return String.format(QueryTemplates.SELECT_BY_PARAMETER.get(), getTableName(clazz),
                getConditionWithEntityId(clazz), idValue);
    }

    @Override
    public String findByParameter(Object object, Class<?> clazz) {
        return String.format(QueryTemplates.SELECT_BY_PARAMETER.get(), getTableName(clazz),

        "username", "'" + object + "'");
    }

    @Override
    public String insert(Object object) {
        return String.format(QueryTemplates.INSERT.get(),
                getTableName(object.getClass()),
                printParamsForQuery(getAllColumns(object.getClass(), object).keySet()),
                printParamsForQuery(getAllColumns(object.getClass(), object).values()));
    }

    @Override
    public String remove(Object condition, Class<?> object) {
        return String.format(QueryTemplates.DELETE_BY_ID.get(),
                getTableName(object),
                getConditionWithEntityId(object),
                condition);
    }

    @Override
    public String update(Object object) {
        String tableName = getTableName(object.getClass());
        String idColumnName = getIdName(object.getClass());
        var queryParameters = getConditionForUpdate(object, object.getClass(), idColumnName);

        String conditionForUpdate = getConditionWithEntityId(object.getClass());
        return String.format(QueryTemplates.UPDATE_BY_ID.get(), tableName,
                queryParameters, conditionForUpdate, getIdValue(object, idColumnName));
    }

    private String getIdValue(Object object, String columnName) {
        var data = getIdColumnValue(object.getClass(), object);
        return data.get(columnName);
    }

    private Map<String, String> getIdColumnValue(Class<?> clazz, Object object) {
        Map<String, String> data = new HashMap<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            Column columnAnnotation = declaredField.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                if (declaredField.getAnnotation(Id.class) != null) {
                    String columnNameFromAnnotation = columnAnnotation.name();
                    String columnName = columnNameFromAnnotation.isEmpty() ? declaredField.getName()
                            : columnNameFromAnnotation;
                    if (object != null) {
                        String value = invokeGetter(object, declaredField.getName());
                        data.put(columnName, value);
                    }
                }
            }
        }
        return data;
    }

    private String getConditionForUpdate(Object object, Class<?> clazz, String idColumn) {
        var map = getAllColumns(clazz, object);
        map.remove(idColumn);
        return map.keySet().stream().
                map(k -> k + "=" + map.get(k)).
                collect(Collectors.joining(", ", "", ""));
    }

    private Map<String, String> getAllColumns(Class<?> clazz, Object object) {
        Map<String, String> data = new HashMap<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            Column columnAnnotation = declaredField.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                if (declaredField.getAnnotation(Id.class) != null) {
                    continue;
                }
                String columnNameFromAnnotation = columnAnnotation.name();
                String columnName = columnNameFromAnnotation.isEmpty() ? declaredField.getName()
                        : columnNameFromAnnotation;
                if (object != null) {
                    String value = invokeGetter(object, declaredField.getName());
                    if (declaredField.getType() == String.class || declaredField.getType() == Date.class) {
                        value = "'" + value + "'";
                    }
                    data.put(columnName, value);
                } else {
                    data.put(columnName, null);
                }
            }
        }
        return data;
    }

    private String getConditionWithEntityId(Class<?> clazz) {
        for (Field declaredField : clazz.getDeclaredFields()) {
            Id idColumn = declaredField.getAnnotation(Id.class);
            if (idColumn != null) {
                Column columnName = declaredField.getAnnotation(Column.class);
                return columnName.name().isEmpty() ? declaredField.getName() : columnName.name();
            } else {
                // find not by id
                Column columnName = declaredField.getAnnotation(Column.class);
                return columnName.name().isEmpty() ? declaredField.getName() : columnName.name();
            }
        }
        return null;
    }

    private String getTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Annotation @Entity should be present");
        }
        Entity clazzAnnotation = clazz.getAnnotation(Entity.class);
        return clazzAnnotation.table().isEmpty() ? clazz.getSimpleName() : clazzAnnotation.table();
    }

    @SneakyThrows
    private String invokeGetter(Object obj, String fieldName) {
        String res = "";
        PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(fieldName, obj.getClass());
            res = valueOf(pd.getReadMethod().invoke(obj));
        } catch (Exception e) {
            log.warn("Couldn't invoke getter method on field => " + fieldName, e);
        }
        return res;
    }

    private String getIdName(Class<?> clazz) {
        for (Field declaredField : clazz.getDeclaredFields()) {
            Id idAnnotation = declaredField.getAnnotation(Id.class);
            if (idAnnotation != null) {
                Column columnAnnotation = declaredField.getAnnotation(Column.class);
                String columnNameFromAnnotation = columnAnnotation != null ? columnAnnotation.name() : null;
                return columnNameFromAnnotation != null && columnNameFromAnnotation.isEmpty() ? declaredField.getName()
                        : columnNameFromAnnotation;
            }
        }
        throw new IllegalArgumentException("Annotation @Id should be present");
    }

    private String printParamsForQuery(Collection<String> params) {
        return join(", ", params);
    }
}
