package ch.zli.m223.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class State {

    public static final String OPEN = "open";
    public static final String REJECTED = "rejected";
    public static final String ACCEPTED = "accepted";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(readOnly = true)
    private long stateId;

    @Column(nullable = false, unique = true)
    private String title;

    @OneToMany(mappedBy = "state")
    private Set<Booking> bookings;

    // Constructor
    public State() {
    }

    public State(String title) {
        this.title = title;
    }

    // Getter/Setter
    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

}
