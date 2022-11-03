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

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(readOnly = true)
    private long bookingId;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "durationId", nullable = true)
    private Duration duration;

    @ManyToOne
    @JoinColumn(name = "stateId", nullable = true)
    private State state;

    @Column(nullable = false)
    private boolean isCancelled;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = true)
    private Member member;

}
