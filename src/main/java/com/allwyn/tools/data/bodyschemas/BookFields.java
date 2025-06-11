package com.allwyn.tools.data.bodyschemas;

import com.allwyn.tools.data.Entity;
import com.allwyn.tools.data.generation.Generate;
import com.allwyn.tools.data.generation.HasName;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

import static java.lang.String.format;

public enum BookFields implements HasName, Generate {

    @Required(generate = true)
    TITLE("title") {
        @Override
        public Object generate() {
            return FAKER.book().title().replace("'", StringUtils.EMPTY);
        }
    },

    @Required(generate = true)
    PAGE_COUNT("pageCount") {
        @Override
        public Object generate() {
            return 100;
        }
    },

    @Required(generate = true)
    COMPLETED("completed") {
        @Override
        public Object generate() {
            return true;
        }
    },

    DESCRIPTION("description") {
        @Override
        public Object generate() {
            return FAKER.lorem().sentence();
        }
    },

    AUTHOR_ID("authorId");


    private final String name;

    BookFields(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private static final Faker FAKER = new Faker(Locale.US);

    @Override
    public Object generate() {
        throw new UnsupportedOperationException(format("Auto data generation is not supported for " +
                                                       "[entity=%s, field=%s]", Entity.BOOKS.getName(), this));
    }
}
