package jr.examples.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jr.examples.entities.Member;
import jr.examples.repositories.MemberRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class MemberControllerTest {
    @Inject
    MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    @Transactional
    void setup() {
        // Attention: IDs get incremented for each test, since it is the same DB
        member = new Member();
        member.username = "JohnDoe";
        member.email = "johndoe@example.com";
        member.address = "123 Main St";
        member.phoneNumber = "123-456-7890";
        memberRepository.persist(member);
    }

    @AfterEach
    @Transactional
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @TestTransaction
    void createMemberWithValidDataShouldReturnCreatedMember() {
        given()
                .contentType("application/json")
                .body("{\"username\":\"New Member\", \"email\":\"newmember@example.com\", \"address\":\"456 Elm St\", \"phoneNumber\":\"987-654-3210\"}")
                .when()
                .post("/api/v1/member")
                .then()
                .statusCode(200)
                .body("username", Matchers.is("New Member"));
    }

    @Test
    @TestTransaction
    void createMemberWithInvalidDataShouldReturnBadRequest() {
        given()
                .contentType("application/json")
                .body("{}") // Missing required fields
                .when()
                .post("/api/v1/member")
                .then()
                .statusCode(400);
    }

    @Test
    @TestTransaction
    void getMembersShouldReturnListOfMembers() {
        given()
                .when()
                .get("/api/v1/member")
                .then()
                .statusCode(200)
                .body("$.size()", Matchers.greaterThan(0));
    }

    @Test
    @TestTransaction
    void getMemberByIdWithValidIdShouldReturnMember() {
        given()
                .when()
                .get(String.format("/api/v1/member/%d", member.id))
                .then()
                .statusCode(200)
                .body("id", Matchers.is(member.id.intValue()));
    }

    @Test
    @TestTransaction
    void getMemberByIdWithInvalidIdShouldReturnNotFound() {
        given()
                .when()
                .get("/api/v1/member/9999") // Assuming no member with this ID exists
                .then()
                .statusCode(404);
    }

    @Test
    @TestTransaction
    void updateMemberWithValidDataShouldReturnUpdatedMember() {
        given()
                .contentType("application/json")
                .body("{\"username\":\"Updated Member\", \"email\":\"updatedmember@example.com\", \"address\":\"789 Pine St\", \"phoneNumber\":\"321-654-9870\"}")
                .when()
                .put(String.format("/api/v1/member/%d", member.id))
                .then()
                .statusCode(200)
                .body("username", Matchers.is("Updated Member"));
    }

    @Test
    @TestTransaction
    void updateMemberWithInvalidDataShouldReturnBadRequest() {
        given()
                .contentType("application/json")
                .body("{}") // Missing required fields
                .when()
                .put(String.format("/api/v1/member/%d", member.id))
                .then()
                .statusCode(400);
    }

    @Test
    @TestTransaction
    void deleteMemberWithValidIdShouldReturnOk() {
        given()
                .when()
                .delete(String.format("/api/v1/member/%d", member.id))
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    void deleteMemberWithInvalidIdShouldReturnNotFound() {
        given()
                .when()
                .delete("/api/v1/member/9999") // Assuming no member with this ID exists
                .then()
                .statusCode(404);
    }
}