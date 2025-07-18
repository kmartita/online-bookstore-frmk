package com.kmartita.tools.data.generation.model;

import com.kmartita.tools.data.generation.Generate;
import com.kmartita.tools.data.generation.HasName;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class TestData<Field extends Enum<Field> & HasName> {

    public static final String SET_OF_FIELDS_TO_GENERATE_MODEL_MUST_NOT_BE_NULL = "Set of fields to generate Model must not be null";

    private final Map<Field, Object> fields;

    public TestData(Map<Field, Object> fields) {
        this.fields = Collections.unmodifiableMap(fields);
    }

    public Map<String, Object> getFieldMap(){
        return fields.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue));
    }

    public Object get(Field field) {
        return fields.get(field);
    }

    public String getString(Field field) {
        if (fields.get(field) instanceof String) {
            return (String) fields.get(field);
        } else if (Objects.isNull(fields.get(field))) {
            return null;
        }
        throw new RuntimeException(format("Object [%s] is not instance of String class", fields.get(field)));
    }

    public Boolean getBoolean(Field field) {
        if (fields.get(field) instanceof Boolean) {
            return (Boolean) fields.get(field);
        }
        throw new RuntimeException(format("Object [%s] is not instance of Boolean class", fields.get(field)));
    }

    public Integer getInteger(Field field) {
        if (fields.get(field) instanceof Integer) {
            return (Integer) fields.get(field);
        } else if (Objects.isNull(fields.get(field))) {
            return null;
        }
        throw new RuntimeException(format("Object [%s] is not instance of Integer class", fields.get(field)));
    }

    public Set<Field> includedFields() {
        return fields.keySet();
    }

    public static <Field extends Enum<Field> & HasName> TestDataBuilder<Field> builder() {
        return new TestDataBuilder<>();
    }

    private TestData<Field> removeFields(Set<Field> fieldsToRemove) {
        TestDataBuilder<Field> builder = TestData.builder();

        List<Field> includedFields = new ArrayList<>(this.includedFields());
        includedFields.removeAll(fieldsToRemove);

        for (Field field : includedFields)
            builder.setField(field, this.fields.get(field));

        return builder.build();
    }

    public TestData<Field> removeFields(Field... fields) {
        return removeFields(new HashSet<>(Arrays.asList(fields)));
    }

    public static <Field extends Enum<Field> & Generate & HasName> TestDataBuilder<Field> preGenerate(Set<Field> fields) {
        Objects.requireNonNull(fields, SET_OF_FIELDS_TO_GENERATE_MODEL_MUST_NOT_BE_NULL);
        TestDataBuilder<Field> newModelBuilder = TestData.builder();
        fields.forEach(field -> newModelBuilder.setField(field, field.generate()));
        return newModelBuilder;
    }

    public <Field extends Enum<Field> & Generate & HasName> TestData<Field> generate(Set<Field> fields) {
        return preGenerate(fields).build();
    }

    public <Field extends Enum<Field> & Generate & HasName> TestData<Field> generate(Field... fields) {
        return generate(new HashSet<>(Arrays.asList(fields)));
    }

    public TestData<Field> edit(Map<Field, Object> fieldsToUpdate) {
        TestDataBuilder<Field> builder = TestData.builder();

        for (Field field : this.includedFields())
            builder.setField(field, this.fields.get(field));

        for (Map.Entry<Field, Object> map : fieldsToUpdate.entrySet())
            builder.setField(map.getKey(), map.getValue());
        return builder.build();
    }

    public TestData<Field> edit(Field fieldToUpdate, Object valueToUpdate) {
        TestDataBuilder<Field> builder = TestData.builder();

        for (Field field : this.includedFields())
            builder.setField(field, this.fields.get(field));

        builder.setField(fieldToUpdate, valueToUpdate);
        return builder.build();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fields == null) ? 0 : fields.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestData<?> other = (TestData<?>) obj;
        if (fields == null) {
            return other.fields == null;
        } else return fields.equals(other.fields);
    }

    @Override
    public String toString() {
        return format("[fields=%s]", getFieldMap());
    }
}
