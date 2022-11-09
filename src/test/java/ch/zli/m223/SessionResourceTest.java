package ch.zli.m223;

import static io.restassured.RestAssured.given;

import java.net.URL;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.Credentials;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class SessionResourceTest {

    @TestHTTPResource("/session")
    URL sessionEndpoint;

    @Test
    @DisplayName("Should check if the login credentials are right")
    public void shouldCheckIfLoginCredentialsAreRight() {

        Credentials credentials = new Credentials();
        credentials.setEmail("kevin.meier@test.ch");
        credentials.setPassword("Password123");

        given().contentType(ContentType.JSON)
                .body(credentials)
                .when().post(sessionEndpoint)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

}
