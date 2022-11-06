package ch.zli.m223.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import ch.zli.m223.model.Booking;

@ApplicationScoped
public class BookingService {

    @Inject
    EntityManager entityManager;

    public List<Booking> getBookingsOfMemberById(long memberId) {
        Query selectMembQuery = entityManager.createQuery("SELECT m FROM Member m WHERE m.memberId = :memberId ");
        selectMembQuery.setParameter("memberId", memberId);
        Object selectedMember = selectMembQuery.getSingleResult();

        TypedQuery<Booking> query = entityManager.createQuery(
                "SELECT b FROM Booking b WHERE b.member = :memberId",
                Booking.class);
        return query.setParameter("memberId", selectedMember).getResultList();
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
        return entityManager.merge(booking);
    }

    @Transactional
    public void setBookingState(long bookingId, String state) {
        Query selectStateQuery = entityManager.createQuery("SELECT s FROM State s WHERE s.title = :state ");
        selectStateQuery.setParameter("state", state);
        Object selectedState = selectStateQuery.getSingleResult();

        Query query = entityManager.createQuery(
                "UPDATE Booking b SET b.state = :state WHERE b.bookingId = :bookingId");
        query.setParameter("bookingId", bookingId);
        query.setParameter("state", selectedState);
        query.executeUpdate();
    }

    @Transactional
    public void cancelBooking(long bookingId) {
        Booking booking = entityManager.find(Booking.class, bookingId);
        booking.setCancelled(true);

    }

}
