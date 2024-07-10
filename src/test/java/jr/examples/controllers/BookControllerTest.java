package jr.examples.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jr.examples.entities.Author;
import jr.examples.entities.Book;
import jr.examples.repositories.AuthorRepository;
import jr.examples.repositories.BookRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
class BookControllerTest {
    @Inject
    AuthorRepository authorRepository;
    @Inject
    BookRepository bookRepository;

    @BeforeEach
    @Transactional
    void setup() {
        // Attention: IDs get incremented for each test, since it is the same DB
        Author author = new Author();
        author.name = "John Doe";
        author.dob = OffsetDateTime.now();
        authorRepository.persist(author);

        Book book = new Book();
        book.title = "Test Book";
        book.genre = "Fiction";
        book.price = 19.99;
        book.author = author;
        bookRepository.persist(book);
    }

    @AfterEach
    @Transactional
    void tearDown() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    @TestTransaction
    void createBookWithValidDataShouldReturnCreatedBook() {
        var authorId = authorRepository.findAll().list().get(0).id; // Get existing ID
        given()
                .contentType("application/json")
                .body(String.format("{\"title\":\"Test Book 2\", \"genre\":\"Science\", \"price\":24.99, \"authorId\":%d}", authorId))
                .when()
                .post("/api/v1/book")
                .then()
                .statusCode(200)
                .body("title", Matchers.is("Test Book 2"))
                .body("genre", Matchers.is("Science"))
                .body("price", Matchers.is(24.99f));
    }

    @Test
    @TestTransaction
    void createBookWithInvalidDataShouldReturnBadRequest() {
        given()
                .contentType("application/json")
                .body("{}") // Missing required fields
                .when()
                .post("/api/v1/book")
                .then()
                .statusCode(400);
    }

    @Test
    @TestTransaction
    void getBooksShouldReturnListOfBooks() {
        given()
                .when()
                .get("/api/v1/book")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThan(0));
    }

    @Test
    @TestTransaction
    void getBookByIdWithValidIdShouldReturnBook() {
        var bookId = bookRepository.findAll().list().get(0).id; // Get existing ID
        given()
                .when()
                .get(String.format("/api/v1/book/%d", bookId))
                .then()
                .statusCode(200)
                .body("id", Matchers.is(bookId.intValue()));
    }

    @Test
    @TestTransaction
    void getBookByIdWithInvalidIdShouldReturnNotFound() {
        given()
                .when()
                .get("/api/v1/book/9999") // Assuming no book with this ID exists
                .then()
                .statusCode(404);
    }

    @Test
    @TestTransaction
    void updateBookWithValidDataShouldReturnUpdatedBook() {
        var bookId = bookRepository.findAll().list().get(0).id; // Get existing book ID
        var authorId = authorRepository.findAll().list().get(0).id; // Get existing author ID
        given()
                .contentType("application/json")
                .body(String.format("{\"title\":\"Updated Test Book\", \"genre\":\"Fantasy\", \"price\":24.99, \"authorId\":%d}", authorId))
                .when()
                .put(String.format("/api/v1/book/%d", bookId))
                .then()
                .statusCode(200)
                .body("title", Matchers.is("Updated Test Book"))
                .body("genre", Matchers.is("Fantasy"))
                .body("price", Matchers.is(24.99f));
    }

    @Test
    @TestTransaction
    void updateBookWithInvalidDataShouldReturnBadRequest() {
        var bookId = bookRepository.findAll().list().get(0).id; // Get existing ID
        given()
                .contentType("application/json")
                .body("{}") // Missing required fields
                .when()
                .put(String.format("/api/v1/book/%d", bookId))
                .then()
                .statusCode(400);
    }

    @Test
    @TestTransaction
    void deleteBookWithValidIdShouldReturnOk() {
        var bookId = bookRepository.findAll().list().get(0).id; // Get existing ID
        given()
                .when()
                .delete(String.format("/api/v1/book/%d", bookId))
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    void deleteBookWithInvalidIdShouldReturnNotFound() {
        given()
                .when()
                .delete("/api/v1/book/9999") // Assuming no book with this ID exists
                .then()
                .statusCode(404);
    }
}