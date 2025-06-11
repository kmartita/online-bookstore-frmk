package com.allwyn.server;

import com.allwyn.tools.data.DataService;
import com.allwyn.tools.data.Entity;
import com.allwyn.tools.data.HasId;
import com.allwyn.tools.data.generation.HasName;
import com.allwyn.tools.data.generation.model.TestData;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.allwyn.tools.EnvManagerUtil.BASE_URL;
import static com.allwyn.tools.JsonUtil.generateJson;
import static java.lang.String.format;

public class ApiRequestBuilder {

    //private static final String BASE_URL = "http://localhost:3000/api/v1/";

    private RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    public RequestSpecification basePath(Entity entity){
        return baseRequest()
                .basePath(entity.getName());
    }

    public <Response extends HasId> RequestSpecification basePath(DataService<Response> data, Entity entity){
        return baseRequest()
                .basePath(format("%s/%s/%s", data.getEntity().getName(), data.getResponse().getId(), entity.getName()));
    }

    public RequestSpecification basePath(Entity entity, int id){
        return baseRequest()
                .basePath(format("%s/%s", entity.getName(), id));
    }

    public <Response extends HasId> RequestSpecification get(DataService<Response> data, Entity entity) {
        return basePath(data, entity);
    }

    public RequestSpecification get(Entity entity) {
        return basePath(entity);
    }

    public RequestSpecification getById(Entity entity, int id) {
        return basePath(entity, id);
    }

    public <Field extends Enum<Field> & HasName> RequestSpecification create(Entity entity,
                                                                             TestData<Field> model) {
        return get(entity)
                .body(generateJson(model).toString());
    }

    public <Field extends Enum<Field> & HasName> RequestSpecification update(Entity entity,
                                                                             int id,
                                                                             TestData<Field> model) {
        return getById(entity, id)
                .body(generateJson(model).toString());
    }
}
