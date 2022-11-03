package ch.zli.m223.model;

import java.util.Set;

import javax.persistence.Entity;

@Entity
public class Duration {
    
    private long durationId;

    private String title;

    private Set<Booking> bookings;

}
