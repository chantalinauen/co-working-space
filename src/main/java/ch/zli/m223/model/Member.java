package ch.zli.m223.model;

import java.util.Set;

import javax.persistence.Entity;

@Entity
public class Member {

    private long memberId;

    private String firstname;

    private String lastname;

    private String emailAddress;

    private String password;

    private int highDeskHight;

    private String standardDrink;

    private boolean isActive;

    private Role role;

    private Set<Booking> bookings;
    
}
