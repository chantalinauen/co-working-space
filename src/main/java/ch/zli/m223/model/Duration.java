package ch.zli.m223.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Duration {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(readOnly = true)
    private long durationId;

    @Column(nullable = false, unique = true)
    private String title;

    @OneToMany(mappedBy = "duration")
    @JsonManagedReference
    private Set<Booking> bookings;

    // Constructor
    public Duration() {
    }

    public Duration(String title) {
        this.title = title;
    }


    // Getter/Setter
    public long getDurationId() {
        return durationId;
    }

    public void setDurationId(long durationId) {
        this.durationId = durationId;
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
