package ch.zli.m223.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(readOnly = true)
    private long bookingId;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    @JoinColumn(name = "durationId", nullable = true)
    private Duration duration;

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    @JoinColumn(name = "stateId", nullable = true)
    private State state;

    @Column(nullable = true)
    private boolean isCancelled;

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    @JoinColumn(name = "memberId", nullable = true)
    private Member member;

    // Constructor
    public Booking() {
    }

    public Booking(LocalDate date, Duration duration, State state, boolean isCancelled, Member member) {
        this.date = date;
        this.duration = duration;
        this.state = state;
        this.isCancelled = isCancelled;
        this.member = member;
    }

    // Getter/Setter
    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
