package ch.zli.m223;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.Member;
import ch.zli.m223.model.Role;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
public class MemberResourceTest {

        private static final String ADMIN_EMAIL = "chantal.inauen@test.ch";

        private static final int MEMBER_ID_KEVIN = 1;

        @TestHTTPResource("/members")
        URL memberEndpoint;

        @Test
        @DisplayName("Should respond with 201")
        public void shouldCheckIfNewMembersCanBeRegistered() {
                Member newMember = new Member();
                newMember.setFirstname("Firstname");
                newMember.setLastname("Lastname");
                newMember.setEmailAddress("peter@test.ch");
                newMember.setPassword("peterPassword");

                given()
                                .contentType(ContentType.JSON)
                                .body(newMember)
                                .when().post(memberEndpoint)
                                .then()
                                .assertThat()
                                .statusCode(HttpStatus.SC_CREATED);
        }

        @Test
        @TestSecurity(user = ADMIN_EMAIL, roles = Role.ADMIN)
        @DisplayName("Should respond with 200")
        public void adminShouldBeAbleToUpdateMember() {
                Member updatedMember = new Member();
                updatedMember.setFirstname("updateKevin");
                updatedMember.setLastname("updateMeier");
                updatedMember.setEmailAddress("updateMail@test.ch");
                updatedMember.setPassword("updatePassword");
                updatedMember.setHighDeskHight(1);

                Member returnedMember = given()
                                .contentType(ContentType.JSON).body(updatedMember)
                                .when().put(memberEndpoint + "/" + 1)
                                .then()
                                .assertThat()
                                .statusCode(HttpStatus.SC_OK)
                                .extract()
                                .as(Member.class);

                assertEquals(returnedMember.getFirstname(), updatedMember.getFirstname());
                assertEquals(returnedMember.getLastname(), updatedMember.getLastname());
                assertEquals(returnedMember.getEmailAddress(), updatedMember.getEmailAddress());
                assertEquals(returnedMember.getPassword(), updatedMember.getPassword());
                assertEquals(returnedMember.getHighDeskHight(), updatedMember.getHighDeskHight());
        }

        @Test
        @TestSecurity(user = ADMIN_EMAIL, roles = Role.ADMIN)
        @DisplayName("Should respond with 204")
        public void adminShouldBeAbleToDeleteMember() {
                given()
                                .contentType(ContentType.JSON)
                                .when().delete(memberEndpoint + "/" + 1)
                                .then()
                                .assertThat()
                                .statusCode(HttpStatus.SC_NO_CONTENT);
        }

        @Test
        @DisplayName("Should respond with 201")
        public void adminShouldBeAbleToCreateMember() {
                Member newMember = new Member();
                newMember.setFirstname("Firstname");
                newMember.setLastname("Lastname");
                newMember.setEmailAddress("ueli@test.ch");
                newMember.setPassword("ueliPassword");
                newMember.setHighDeskHight(0);

                given()
                                .contentType(ContentType.JSON)
                                .body(newMember)
                                .when().post(memberEndpoint)
                                .then()
                                .assertThat()
                                .statusCode(HttpStatus.SC_CREATED)
                                .body("memberId", not(0));
        }

        @Test
        @TestSecurity(user = ADMIN_EMAIL, roles = Role.ADMIN)
        @DisplayName("Should respond with 200")
        public void adminShouldBeAbleToChangeRoleOfMember() {
                given()
                                .when().put(memberEndpoint + "/rights/" + MEMBER_ID_KEVIN + "/" + Role.ADMIN)
                                .then()
                                .assertThat()
                                .statusCode(HttpStatus.SC_NO_CONTENT);
        }

        @Test
        @DisplayName("Should respond with Code 401 if authorization is missing")
        public void shouldRespondWithCode401IfAuthorizationIsMissing() {
                given()
                                .when().get(memberEndpoint)
                                .then()
                                .statusCode(401);
        }

        @Test
        @TestSecurity(user = ADMIN_EMAIL, roles = Role.ADMIN)
        @DisplayName("Should respond with Code 200")
        public void adminShouldGetMembers() {
                given()
                                .when().get(memberEndpoint)
                                .then()
                                .statusCode(200)
                                .body(not("[]"));
        }

}
