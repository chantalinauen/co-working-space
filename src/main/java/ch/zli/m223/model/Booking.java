package ch.zli.m223.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.swing.text.StyledEditorKit.BoldAction;

@Entity
public class Booking {
    
    private long bookingId;

    private LocalDate date;

    private Duration duration;

    private State state;

    private boolean isCancelled;

    private Member member;
    
}
