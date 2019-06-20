package es.msanchez.templates.java.spring.builders.generics;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Slf4j
public abstract class RandomBuilder<TYPE> extends RandomsImpl {

    protected void randomize(final List<TYPE> types) {
        types.forEach(this::randomize);
    }

    private void randomize(final TYPE type) {
        final Field[] fields = type.getClass().getDeclaredFields();
        log.trace("Found '{}' fields for class '{}'", fields.length, type.getClass());
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            this.randomizeField(type, field);
        });
    }

    private void randomizeField(final TYPE type,
                                final Field field) {
        try {
            final String fieldName = field.getType().getName();
            if (fieldName.contains("Long")) {
                field.set(type, this.randomPositiveLong());
            } else if (fieldName.contains("String")) {
                field.set(type, this.randomAlphanumeric());
            } else if (fieldName.contains("Integer")) {
                field.set(type, this.randomPositiveInteger());
            } else {
                log.error("Tried to randomize the field type '{}' but it's not known", fieldName);
            }
        } catch (final IllegalAccessException ex) {
            log.error("Error on randomize field with reflection", ex);
        }
    }

}
