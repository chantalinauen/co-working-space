package ch.zli.m223.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.smallrye.common.constraint.NotNull;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(readOnly = true)
    private long memberId;

    @NotBlank
    @Column(nullable = false)
    private String firstname;

    @NotBlank
    @Column(nullable = false)
    private String lastname;

    @Email
    @NotNull
    @Column(nullable = false, unique = true)
    private String emailAddress;

    @Size(min = 10, max = 30, message = "Your password must have 10 to 30 characters.")
    @Column(nullable = false)
    private String password;

    @PositiveOrZero
    @Max(10)
    @Column(nullable = false)
    private int highDeskHight;

    @Column(nullable = true)
    private String standardDrink;

    @Column(nullable = true)
    private boolean isActive = true;

    @ManyToOne(optional = true)
    @JsonIgnoreProperties("members")
    @JoinColumn(name = "roleId")
    private Role role;

    @OneToMany(mappedBy = "member")
    private Set<Booking> bookings;

    // Constructor
    public Member() {
    }

    public Member(String firstname, String lastname, String emailAddress, String password, int highDeskHight,
            String standardDrink, boolean isActive, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.password = password;
        this.highDeskHight = highDeskHight;
        this.standardDrink = standardDrink;
        this.isActive = isActive;
        this.role = role;
    }

    // Getter/Setter
    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHighDeskHight() {
        return highDeskHight;
    }

    public void setHighDeskHight(int highDeskHight) {
        this.highDeskHight = highDeskHight;
    }

    public String getStandardDrink() {
        return standardDrink;
    }

    public void setStandardDrink(String standardDrink) {
        this.standardDrink = standardDrink;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

}
