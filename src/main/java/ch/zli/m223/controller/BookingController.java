package ch.zli.m223.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.Booking;
import ch.zli.m223.model.Member;
import ch.zli.m223.model.Role;
import ch.zli.m223.model.State;
import ch.zli.m223.service.BookingService;
import ch.zli.m223.service.MemberService;

@Path("/bookings")
@Tag(name = "Booking", description = "Handling of bookings")
@RolesAllowed({ Role.ADMIN, Role.MEMBER })
public class BookingController {

    @Inject
    BookingService bookingService;
    @Inject
    MemberService memberService;

    @Path("{memberId}")
    @RolesAllowed({ Role.ADMIN, Role.MEMBER })
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lists all bookings of a member.", description = "Returns a list of the bookings.")
    public List<Booking> listBookingsOfMember(@PathParam("memberId") long id, SecurityContext ctx) {
        List<Booking> bookingsOfMember;
        if (ctx.isUserInRole(Role.ADMIN)) {
            bookingsOfMember = bookingService.getBookingsOfMember(id);
        } else {
            Optional<Member> member = memberService.findByEmail(ctx.getUserPrincipal().getName());
            if (member.isPresent() && member.get().getMemberId() == id) {
                bookingsOfMember = bookingService.getBookingsOfMember(id);
            } else {
                throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN).build());
            }
        }
        return bookingsOfMember;
    }

    @RolesAllowed(Role.ADMIN)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lists all bookings of all members.", description = "Returns a list of the existing bookings of all members.")
    public List<Booking> listAllBookings() {
        return bookingService.getAllBookings();
    }

    @RolesAllowed({ Role.ADMIN, Role.MEMBER })
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new booking.", description = "Creates a new booking and returns it.")
    public Booking createBooking(@Valid Booking booking) {
        return bookingService.createBooking(booking);
    }

    @Path("/{bookingId}")
    @RolesAllowed(Role.ADMIN)
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a booking.", description = "Updates a booking by its id.")
    public Booking update(@PathParam("bookingId") long id, @Valid Booking booking) {
        return bookingService.updateBooking(id, booking);
    }

    @Path("/accept/{bookingId}")
    @RolesAllowed(Role.ADMIN)
    @PUT
    @Operation(summary = "Accepts a booking.", description = "Sets the state of a booking to 'accepted'.")
    public void acceptBooking(@PathParam("bookingId") long id) {
        bookingService.setBookingState(id, State.ACCEPTED);
    }

    @Path("/reject/{bookingId}")
    @RolesAllowed(Role.ADMIN)
    @PUT
    @Operation(summary = "Rejects a booking.", description = "Sets the state of a booking to 'rejected'.")
    public void rejectBooking(@PathParam("bookingId") long id) {
        bookingService.setBookingState(id, State.REJECTED);
    }

    @Path("/{bookingId}")
    @RolesAllowed({ Role.ADMIN, Role.MEMBER })
    @DELETE
    @Operation(summary = "Deletes a booking.", description = "Deletes a booking by its id, respectivly set isCancelled state. For better readability it's a DELETE request.")
    public void delete(@PathParam("bookingId") long id) {
        bookingService.cancelBooking(id);
    }

}
