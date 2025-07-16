package com.kmartita.tools.helpers;

import com.kmartita.server.ApiRequestBuilder;
import com.kmartita.tools.data.Entity;
import com.kmartita.tools.data.responses.Author;
import com.kmartita.tools.data.responses.Book;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class EntityService {

    private final ApiRequestBuilder request;

    public EntityService() {
        request = new ApiRequestBuilder();
    }

    private <Endpoint> List<Endpoint> getEntities(Entity entity, Class<Endpoint> entityType) {
        List<Endpoint> entities = given()
                .spec(request.basePath(entity))
                .when()
                .get()
                .jsonPath()
                .getList(entity.getName(), entityType);

        return entities != null ? entities : Collections.emptyList();
    }

    public List<Book> getBooks() {
        return getEntities(Entity.BOOKS, Book.class);
    }

    public List<Author> getAuthors() {
        return getEntities(Entity.AUTHORS, Author.class);
    }

    public Author getExistingAuthor() {
        return getAuthors().stream().findAny().orElseThrow(() -> new RuntimeException("The authors do not exist"));
    }
}
