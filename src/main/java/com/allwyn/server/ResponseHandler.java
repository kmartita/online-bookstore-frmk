package com.allwyn.server;

import com.allwyn.tools.JsonUtil;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

public class ResponseHandler {

    private final Response response;

    public ResponseHandler(Response response) {
        this.response = response;
    }

    public ResponseHandler validate(ResponseSpecification responseSpecification) {
        this.response.then().spec(responseSpecification).assertThat();
        return this;
    }

    public <Endpoint> Endpoint extractAs(Class<Endpoint> clazz) {
        return JsonUtil.readJson(this.response.then().extract().body().asString(), clazz);
    }
}
