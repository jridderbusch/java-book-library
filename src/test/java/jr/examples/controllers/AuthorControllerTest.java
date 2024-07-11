package jr.examples.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jr.examples.entities.Author;
import jr.examples.repositories.AuthorRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static io.restassured.RestAssured.given;

@QuarkusTest
class AuthorControllerTest {
    @Inject
    AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    @Transactional
    void setup() {
        // Attention: IDs get incremented for each test, since it is the same DB
        author = new Author();
        author.name = "Jane Doe";
        author.dob = OffsetDateTime.now();
        authorRepository.persist(author);
    }

    @AfterEach
    @Transactional
    void tearDown() {
        authorRepository.deleteAll();
    }

    @Test
    @TestTransaction
    void createAuthorWithValidDataShouldReturnCreatedAuthor() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"New Author\", \"dob\":\"2022-03-10T12:15:50-04:00\"}")
                .when()
                .post("/api/v1/author")
                .then()
                .statusCode(200)
                .body("name", Matchers.is("New Author"));
    }

    @Test
    @TestTransaction
    void createAuthorWithInvalidDataShouldReturnBadRequest() {
        given()
                .contentType("application/json")
                .body("{}") // Missing required fields
                .when()
                .post("/api/v1/author")
                .then()
                .statusCode(400);
    }

    @Test
    @TestTransaction
    void getAuthorsShouldReturnListOfAuthors() {
        given()
                .when()
                .get("/api/v1/author")
                .then()
                .statusCode(200)
                .body("$.size()", Matchers.greaterThan(0));
    }

    @Test
    @TestTransaction
    void getAuthorByIdWithValidIdShouldReturnAuthor() {
        given()
                .when()
                .get(String.format("/api/v1/author/%d", author.id))
                .then()
                .statusCode(200)
                .body("id", Matchers.is(author.id.intValue()));
    }

    @Test
    @TestTransaction
    void getAuthorByIdWithInvalidIdShouldReturnNotFound() {
        given()
                .when()
                .get("/api/v1/author/9999") // Assuming no author with this ID exists
                .then()
                .statusCode(404);
    }

    @Test
    @TestTransaction
    void updateAuthorWithValidDataShouldReturnUpdatedAuthor() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"Updated Author\", \"dob\":\"2022-03-10T12:15:50-04:00\"}")
                .when()
                .put(String.format("/api/v1/author/%d", author.id))
                .then()
                .statusCode(200)
                .body("name", Matchers.is("Updated Author"));
    }

    @Test
    @TestTransaction
    void updateAuthorWithInvalidDataShouldReturnBadRequest() {
        given()
                .contentType("application/json")
                .body("{}") // Missing required fields
                .when()
                .put(String.format("/api/v1/author/%d", author.id))
                .then()
                .statusCode(400);
    }

    @Test
    @TestTransaction
    void deleteAuthorWithValidIdShouldReturnOk() {
        given()
                .when()
                .delete(String.format("/api/v1/author/%d", author.id))
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    void deleteAuthorWithInvalidIdShouldReturnNotFound() {
        given()
                .when()
                .delete("/api/v1/author/9999") // Assuming no author with this ID exists
                .then()
                .statusCode(404);
    }
}