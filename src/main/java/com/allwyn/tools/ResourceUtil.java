package com.allwyn.tools;

import com.allwyn.tools.data.bodyschema.Required;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class ResourceUtil {

    public static <Field extends Enum<Field>> Set<Field> getRequiredFields(Class<Field> enumClass) {
        return Stream.of(enumClass.getEnumConstants())
                .filter(field -> {
                    Required annotation = FieldUtils.getField(enumClass, field.name()).getAnnotation(Required.class);
                    return annotation != null && annotation.generate();
                }).collect(Collectors.toSet());
    }
}
