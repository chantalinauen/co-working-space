package ch.zli.m223.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import ch.zli.m223.model.Booking;

@ApplicationScoped
public class BookingService {

    @Inject
    EntityManager entityManager;

    public List<Booking> getBookingsOfMember(long memberId) {
        try {
            Object selectedMember = entityManager.createQuery("SELECT m FROM Member m WHERE m.memberId = :memberId ")
                    .setParameter("memberId", memberId)
                    .getSingleResult();

            return entityManager.createQuery(
                    "SELECT b FROM Booking b WHERE b.member = :memberId",
                    Booking.class)
                    .setParameter("memberId", selectedMember)
                    .getResultList();
        } catch (NoResultException e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

    }

    public List<Booking> getAllBookings() {
        return entityManager.createQuery("FROM Booking", Booking.class)
                .getResultList();
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        return entityManager.merge(booking);
    }

    @Transactional
    public Booking updateBooking(long bookingId, Booking booking) {
        booking.setBookingId(bookingId);
        Booking updatedBooking = entityManager.merge(booking);

        if (updatedBooking == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return updatedBooking;
        }
    }

    @Transactional
    public void setBookingState(long bookingId, String state) {
        Object selectedState = entityManager.createQuery("SELECT s FROM State s WHERE s.title = :state ")
                .setParameter("state", state)
                .getSingleResult();

        int updatedBooking = entityManager.createQuery(
                "UPDATE Booking b SET b.state = :state WHERE b.bookingId = :bookingId")
                .setParameter("bookingId", bookingId)
                .setParameter("state", selectedState)
                .executeUpdate();

        if (updatedBooking == 0) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Transactional
    public void cancelBooking(long bookingId) {
        Booking booking = entityManager.find(Booking.class, bookingId);
        if (booking == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            booking.setCancelled(true);
        }
    }

}