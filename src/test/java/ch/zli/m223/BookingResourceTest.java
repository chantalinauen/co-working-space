package ch.zli.m223;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.net.URL;
import java.time.LocalDate;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.Booking;
import ch.zli.m223.model.Member;
import ch.zli.m223.model.Role;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
public class BookingResourceTest {

    private static final String ADMIN_EMAIL = "chantal.inauen@test.ch";
    private static final String MEMBER_KEVIN_EMAIL = "kevin.meier@test.ch";

    private static final int BOOKING_ID_BY_KEVIN = 1;
    private static final int BOOKING_ID_BY_LUKAS = 2;

    @TestHTTPResource("/bookings")
    URL bookingEndpoint;

    @Test
    @TestSecurity(user = MEMBER_KEVIN_EMAIL, roles = Role.MEMBER)
    @DisplayName("Should respond with 403")
    public void memberShouldNotBeAbleDeleteBookingOfOthers() {
        given()
                .contentType(ContentType.JSON)
                .when().delete(bookingEndpoint + "/" + BOOKING_ID_BY_LUKAS)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @TestSecurity(user = MEMBER_KEVIN_EMAIL, roles = Role.MEMBER)
    @DisplayName("Should respond with 204")
    public void memberShouldBeAbleToDeleteOwnBooking() {
        given()
                .contentType(ContentType.JSON)
                .when().delete(bookingEndpoint + "/" + BOOKING_ID_BY_KEVIN)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @TestSecurity(user = MEMBER_KEVIN_EMAIL, roles = Role.MEMBER)
    @DisplayName("Should respond with 201")
    public void memberShouldBeAbleToCreateBooking() {
        final int MEMBER_ID_KEVIN = 1;
        Member member = new Member();
        member.setMemberId(MEMBER_ID_KEVIN);
        Booking newBooking = new Booking();
        newBooking.setDate(LocalDate.of(2022, 12, 01));
        newBooking.setMember(member);

        given()
                .contentType(ContentType.JSON)
                .body(newBooking)
                .when().post(bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("bookingId", not(0));
    }

    @Test
    @TestSecurity(user = ADMIN_EMAIL, roles = Role.ADMIN)
    @DisplayName("Should respond with 200")
    public void adminShouldBeAbleToUpdateBookingOfMember() {
        Booking updatedBooking = new Booking();
        updatedBooking.setDate(LocalDate.of(2022, 12, 8));

        given()
                .contentType(ContentType.JSON).body(updatedBooking)
                .when().put(bookingEndpoint + "/" + BOOKING_ID_BY_KEVIN)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("date", is("2022-12-08"));
    }

    @Test
    @TestSecurity(user = ADMIN_EMAIL, roles = Role.ADMIN)
    @DisplayName("Should respond with 204")
    public void adminShouldBeAbleToDeleteBookingOfMember() {
        given()
                .contentType(ContentType.JSON)
                .when().delete(bookingEndpoint + "/" + BOOKING_ID_BY_LUKAS)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @TestSecurity(user = ADMIN_EMAIL, roles = Role.ADMIN)
    @DisplayName("Should respond with 201")
    public void adminShouldBeAbleToCreateBooking() {
        Booking newBooking = new Booking();
        newBooking.setDate(LocalDate.of(2022, 12, 01));

        given()
                .contentType(ContentType.JSON)
                .body(newBooking)
                .when().post(bookingEndpoint)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("bookingId", not(0));
    }

}
