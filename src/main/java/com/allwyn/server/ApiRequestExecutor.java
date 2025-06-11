package com.allwyn.server;

import com.allwyn.tools.data.Entity;
import com.allwyn.tools.data.generation.HasName;
import com.allwyn.tools.data.generation.model.TestData;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiRequestExecutor {

    private final ApiRequestBuilder request;
    private Response response;

    public ApiRequestExecutor() {
        request = new ApiRequestBuilder();
    }

    public ResponseHandler get(Entity entity) {
        response = given()
                .spec(request.get(entity))
                .when()
                .get();
        return new ResponseHandler(response);
    }

    public ResponseHandler getById(Entity entity, int id) {
        response = given()
                .spec(request.getById(entity, id))
                .when()
                .get();
        return new ResponseHandler(response);
    }

    public <Field extends Enum<Field> & HasName> ResponseHandler post(Entity entity,
                                                                      TestData<Field> model) {
        response = given()
                .spec(request.create(entity, model))
                .when()
                .post();
        return new ResponseHandler(response);
    }

    public <Field extends Enum<Field> & HasName> ResponseHandler put(Entity entity,
                                                                     int id,
                                                                     TestData<Field> model) {
        response = given()
                .spec(request.update(entity, id, model))
                .when()
                .put();
        return new ResponseHandler(response);
    }

    public ResponseHandler delete(Entity entity, int id) {
        response = given()
                .spec(request.getById(entity, id))
                .when()
                .delete();
        return new ResponseHandler(response);
    }
}
