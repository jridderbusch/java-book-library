package jr.examples.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jr.examples.entities.Author;
import jr.examples.entities.Book;
import jr.examples.entities.Loan;
import jr.examples.entities.Member;
import jr.examples.repositories.AuthorRepository;
import jr.examples.repositories.BookRepository;
import jr.examples.repositories.LoanRepository;
import jr.examples.repositories.MemberRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static io.restassured.RestAssured.given;

@QuarkusTest
class LoanControllerTest {
    @Inject
    LoanRepository loanRepository;
    @Inject
    AuthorRepository authorRepository;
    @Inject
    BookRepository bookRepository;
    @Inject
    MemberRepository memberRepository;

    private Member member;
    private Book book;

    @BeforeEach
    @Transactional
    void setup() {
        Author author = new Author();
        author.name = "Author Name";
        author.dob = OffsetDateTime.now().minusYears(30);
        authorRepository.persist(author);

        book = new Book();
        book.title = "Test Book";
        book.genre = "Fiction";
        book.price = 19.99;
        book.author = author;
        bookRepository.persist(book);

        member = new Member();
        member.username = "Member Username";
        member.email = "member@example.com";
        member.address = "123 Elm St";
        member.phoneNumber = "123-456-7890";
        memberRepository.persist(member);

        Loan loan = new Loan();
        loan.member = member;
        loan.book = book;
        loan.lendDate = LocalDate.now();
        loan.dueDate = LocalDate.now().plusDays(30);
        loanRepository.persist(loan);
    }

    @AfterEach
    @Transactional
    void tearDown() {
        loanRepository.deleteAll();
        bookRepository.deleteAll();
        memberRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    @TestTransaction
    void createLoanWithValidDataShouldReturnCreatedLoan() {
        given()
                .contentType("application/json")
                .body(String.format("{\"memberId\":%d, \"bookId\":%d, \"lendDate\":\"2023-01-01\", \"dueDate\":\"2023-02-01\"}", member.id, book.id))
                .when()
                .post("/api/v1/loan")
                .then()
                .statusCode(200)
                .body("memberId", Matchers.is(member.id.intValue()))
                .body("bookId", Matchers.is(book.id.intValue()));
    }

    @Test
    @TestTransaction
    void createLoanWithInvalidDataShouldReturnBadRequest() {
        given()
                .contentType("application/json")
                .body("{}") // Missing required fields
                .when()
                .post("/api/v1/loan")
                .then()
                .statusCode(400);
    }

    @Test
    @TestTransaction
    void getLoansShouldReturnListOfLoans() {
        given()
                .when()
                .get("/api/v1/loan")
                .then()
                .statusCode(200)
                .body("$.size()", Matchers.greaterThan(0));
    }

    @Test
    @TestTransaction
    void getLoanByIdWithValidIdShouldReturnLoan() {
        var loanId = loanRepository.findAll().list().get(0).id;
        given()
                .when()
                .get("/api/v1/loan/" + loanId)
                .then()
                .statusCode(200)
                .body("id", Matchers.is(loanId.intValue()));
    }

    @Test
    @TestTransaction
    void getLoanByIdWithInvalidIdShouldReturnNotFound() {
        given()
                .when()
                .get("/api/v1/loan/9999") // // Assuming no loan with this ID exists
                .then()
                .statusCode(404);
    }

    @Test
    @TestTransaction
    void  updateLoanWithValidDataShouldReturnUpdatedLoan() {
        var loanId = loanRepository.findAll().list().get(0).id;
        given()
                .contentType("application/json")
                .body(String.format("{\"memberId\":%d, \"bookId\":%d, \"lendDate\":\"2023-03-01\", \"dueDate\":\"2023-04-01\"}", member.id, book.id))
                .when()
                .put("/api/v1/loan/" + loanId)
                .then()
                .statusCode(200)
                .body("lendDate", Matchers.is("2023-03-01"))
                .body("dueDate", Matchers.is("2023-04-01"));
    }

    @Test
    @TestTransaction
    void updateLoanWithInvalidDataShouldReturnBadRequest() {
        var loanId = loanRepository.findAll().list().get(0).id;
        given()
                .contentType("application/json")
                .body("{}") // Missing required fields for update
                .when()
                .put("/api/v1/loan/" + loanId)
                .then()
                .statusCode(400);
    }

    @Test
    @TestTransaction
    void deleteLoanWithValidIdShouldReturnOk() {
        var loanId = loanRepository.findAll().list().get(0).id;
        given()
                .when()
                .delete("/api/v1/loan/" + loanId)
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    void deleteLoanWithInvalidIdShouldReturnNotFound() {
        given()
                .when()
                .delete("/api/v1/loan/9999") // Assuming no loan with this ID exists
                .then()
                .statusCode(404);
    }
}