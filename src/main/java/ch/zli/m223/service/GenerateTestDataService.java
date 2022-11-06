package ch.zli.m223.service;

import java.time.LocalDate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.Booking;
import ch.zli.m223.model.Duration;
import ch.zli.m223.model.Member;
import ch.zli.m223.model.Role;
import ch.zli.m223.model.State;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;

@IfBuildProfile("dev")
@ApplicationScoped
public class GenerateTestDataService {
    
    @Inject
    EntityManager entityManager;
  
    @Transactional
    void generateTestData(@Observes StartupEvent event) {

        // Durations
        Duration durationMorning = new Duration("morning");
        Duration durationAfternoon = new Duration("afternoon");
        Duration durationDay = new Duration("day");
        entityManager.persist(durationMorning);
        entityManager.persist(durationAfternoon);
        entityManager.persist(durationDay);

        // Roles
        Role roleMember = new Role("member");
        Role roleAdmin = new Role("administrator");
        entityManager.persist(roleMember);
        entityManager.persist(roleAdmin);

        // States
        State stateAccept = new State(State.ACCEPTED);
        State stateReject = new State(State.REJECTED);
        State stateOpen = new State(State.OPEN);
        entityManager.persist(stateAccept);
        entityManager.persist(stateReject);
        entityManager.persist(stateOpen);

        // Members
        Member memberKevin = new Member(
            "Kevin", "Meier", "kevin.meier@test.ch", 
        "Password123", 7, null, true, roleMember);

        Member memberLukas = new Member(
            "Lukas", "Grunder", "lukas.grunder@test.ch", 
        "MeinPasswort", 9, "Tony Mate", false, roleMember);
        
        Member memberChantal = new Member(
            "Chantal", "Inauen", "chantal.inauen@test.ch", 
        "Password789", 0, "Cola Zero", true, roleAdmin);

        entityManager.persist(memberKevin);
        entityManager.persist(memberLukas);
        entityManager.persist(memberChantal);

        // Bookings
        Booking bookingOne = new Booking(LocalDate.now().plusMonths(3), durationMorning, stateOpen, false, memberKevin);
        Booking bookingTwo = new Booking(LocalDate.now().minusMonths(2), durationAfternoon, stateAccept, false, memberLukas  );
        Booking bookingThree = new Booking(LocalDate.now().plusMonths(7), durationAfternoon, stateReject, false, memberKevin  );

        entityManager.persist(bookingOne);
        entityManager.persist(bookingTwo);
        entityManager.persist(bookingThree);
    }
  
}
