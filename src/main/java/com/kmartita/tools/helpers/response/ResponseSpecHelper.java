package com.kmartita.tools.helpers.response;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

import static com.kmartita.tools.AllureUtil.attachJsonFile;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecHelper {

    public static ResponseSpecification specOnSchemaValidating(String path) {
        attachJsonFile(path);

        return new ResponseSpecBuilder()
                .expectBody(matchesJsonSchemaInClasspath(path))
                .build();
    }

    public static ResponseSpecification specOnCreating() {
        return new ResponseSpecBuilder()
                .expectBody("id", notNullValue())
                .build();
    }

    public static ResponseSpecification specOnDeleting() {
        return new ResponseSpecBuilder()
                .expectBody(StringUtils.EMPTY, equalTo(Collections.emptyMap()))
                .build();
    }
}
