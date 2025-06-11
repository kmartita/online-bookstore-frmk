package com.allwyn.books;

import com.allwyn.BaseTest;
import com.allwyn.server.ResponseHandler;
import com.allwyn.tools.data.Entity;
import com.allwyn.tools.data.bodyschema.BookFields;
import com.allwyn.tools.data.generation.model.TestData;
import com.allwyn.tools.data.responses.Book;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.*;

import static com.allwyn.tools.ResourceUtil.getRequiredFields;
import static com.allwyn.tools.helpers.StatusCodeData.*;
import static com.allwyn.tools.helpers.response.ResponseSpecHelper.specOnCreating;
import static com.allwyn.tools.helpers.response.ResponseSpecHelper.specOnSchemaValidating;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;

@Feature("Book")
public class BookScenarioTest extends BaseTest {

    private final String ONE_BOOK_EXISTS = "One book exists";
    private final String NEGATIVE_TYPE_DATA = "Negative type data";

    private static final TestData<BookFields> REQUIRED_DATA = TestData
            .preGenerate(getRequiredFields(BookFields.class))
            .build();

    private int bookId;

    @Step("Delete all books")
    private void deleteBooks() {
        deleteEntities(entityService.getBooks(), Entity.BOOKS);
    }

    @BeforeClass(alwaysRun = true) @AfterMethod(alwaysRun = true)
    public void clean() {
        deleteBooks();
    }

    @BeforeMethod(onlyForGroups = ONE_BOOK_EXISTS, alwaysRun = true)
    public void oneBookExists() {
        bookId = apiService
                .post(Entity.BOOKS, REQUIRED_DATA)
                .validate(specOnSchemaValidating("schemas/book.json"))
                .extractAs(Book.class)
                .getId();
    }

    @DataProvider(name = NEGATIVE_TYPE_DATA)
    public Object[][] negativeResponseSpaceDataProvider() {
        TestData<BookFields> testData = TestData
                .preGenerate(getRequiredFields(BookFields.class))
                .build();

        return new Object[][]{
                {testData.edit(BookFields.COMPLETED, 0.8), format("%s must be a boolean", BookFields.COMPLETED.getName())},
                {testData.edit(BookFields.PAGE_COUNT, "text"), format("%s must be an integer", BookFields.PAGE_COUNT.getName())},
        };
    }



    @Story("GET: /Books")
    @Test(priority = 1, alwaysRun = true)
    public void validateGetAnEmptyListOfBooks() {
        ResponseSpecification specification = new ResponseSpecBuilder()
                .expectStatusCode(STATUS_CODE_200)
                .expectStatusLine(OK)
                .expectBody("Books", is(empty()))
                .build();

        apiService
                .get(Entity.BOOKS)
                .validate(specification);
    }

    @Story("GET: /Books")
    @Test(priority = 2, groups = ONE_BOOK_EXISTS, alwaysRun = true)
    public void validateGetAListOfExistingBooks() {
        ResponseSpecification specification = new ResponseSpecBuilder()
                .expectStatusCode(STATUS_CODE_200)
                .expectStatusLine(OK)
                .expectBody("Books", not(empty()))
                .expectBody("Books.id", everyItem(notNullValue()))
                .expectBody("Books.title", everyItem(notNullValue()))
                .expectBody("Books.pageCount", everyItem(notNullValue()))
                .expectBody("Books.completed", everyItem(notNullValue()))
                .build();

        apiService
                .get(Entity.BOOKS)
                .validate(specification);
    }

    @Story("GET: /Books/{id}")
    @Test(priority = 3, groups = ONE_BOOK_EXISTS, alwaysRun = true)
    public void validateGetDetailsOfBookByItsId() {
        ResponseSpecification specification = new ResponseSpecBuilder()
                .expectStatusCode(STATUS_CODE_200)
                .expectStatusLine(OK)
                .expectBody("id", equalTo(bookId))
                .expectBody("title", equalTo(REQUIRED_DATA.getString(BookFields.TITLE)))
                .expectBody("pageCount", equalTo(REQUIRED_DATA.getInteger(BookFields.PAGE_COUNT)))
                .expectBody("completed", is(REQUIRED_DATA.getBoolean(BookFields.COMPLETED)))
                .expectBody("description", nullValue())
                .expectBody("authorId", nullValue())
                .build();

        apiService
                .getById(Entity.BOOKS, bookId)
                .validate(specification);
    }

    @Story("POST: /Books")
    @Test(priority = 4, alwaysRun = true)
    public void validatePostABookWithPositiveData() {
        TestData<BookFields> data = TestData
                .preGenerate(getRequiredFields(BookFields.class))
                .setField(BookFields.DESCRIPTION, BookFields.DESCRIPTION.generate())
                .build()
                .edit(BookFields.COMPLETED, false);

        ResponseSpecification specification = new ResponseSpecBuilder()
                .expectStatusCode(STATUS_CODE_201)
                .expectStatusLine(CREATED)
                .expectBody("title", equalTo(data.getString(BookFields.TITLE)))
                .expectBody("pageCount", equalTo(data.getInteger(BookFields.PAGE_COUNT)))
                .expectBody("completed", is(data.getBoolean(BookFields.COMPLETED)))
                .expectBody("description", equalTo(data.getString(BookFields.DESCRIPTION)))
                .expectBody("authorId", nullValue())
                .build();

        ResponseHandler response = apiService
                .post(Entity.BOOKS, data)
                .validate(specOnSchemaValidating("schemas/book.json"))
                .validate(specification)
                .validate(specOnCreating());

        int bookId = response
                .extractAs(Book.class)
                .getId();

        ResponseSpecification specBySuccess = new ResponseSpecBuilder()
                .expectStatusCode(STATUS_CODE_200)
                .expectStatusLine(OK)
                .expectBody("id", equalTo(bookId))
                .build();

        apiService.getById(Entity.BOOKS, bookId)
                .validate(specBySuccess);
    }

    @Story("POST: /Books")
    @Test(priority = 5, dataProvider = NEGATIVE_TYPE_DATA, alwaysRun = true)
    @Parameters({"Data:", "Error:"})
    public void validatePostABookWithNegativeTypeData(TestData<BookFields> data,
                                                      String error) {
        ResponseSpecification specByFailure = new ResponseSpecBuilder()
                .expectStatusCode(STATUS_CODE_400)
                .expectStatusLine(BAD_REQUEST)
                .expectBody("message", equalTo(error))
                .build();

        apiService
                .post(Entity.BOOKS, data)
                .validate(specByFailure);
    }

    @Story("PUT: /Books/{id}")
    @Test(priority = 6, groups = ONE_BOOK_EXISTS, alwaysRun = true)
    public void validateUpdateABookByItsId() {
        String title = apiService
                .getById(Entity.BOOKS, bookId)
                .extractAs(Book.class)
                .getTitle();

        TestData<BookFields> updatedData = TestData
                .preGenerate(getRequiredFields(BookFields.class))
                .build()
                .edit(BookFields.TITLE, "Updated");

        ResponseSpecification specification = new ResponseSpecBuilder()
                .expectStatusCode(STATUS_CODE_200)
                .expectStatusLine(OK)
                .expectBody("id", equalTo(bookId))
                .expectBody("title", allOf(not(equalTo(title)), equalTo("Updated")))
                .build();

        apiService
                .put(Entity.BOOKS, bookId, updatedData)
                .validate(specification);
    }

    @Story("DELETE: /Books/{id}")
    @Test(priority = 7, groups = ONE_BOOK_EXISTS, alwaysRun = true)
    public void validateDeleteABookByItsId() {
        ResponseSpecification specification = new ResponseSpecBuilder()
                .expectStatusCode(STATUS_CODE_204)
                .expectStatusLine(NO_CONTENT)
                .build();

        apiService
                .delete(Entity.BOOKS, bookId)
                .validate(specification);
    }
}