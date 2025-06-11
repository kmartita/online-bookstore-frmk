package com.allwyn.tools.helpers;

import com.allwyn.server.ApiRequestBuilder;
import com.allwyn.tools.data.Entity;
import com.allwyn.tools.data.responses.Author;
import com.allwyn.tools.data.responses.Book;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class EntityService {

    private final ApiRequestBuilder request;

    public EntityService() {
        request = new ApiRequestBuilder();
    }

    private <R> List<R> getEntities(Entity entity, Class<R> entityType) {
        List<R> entities = given()
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
