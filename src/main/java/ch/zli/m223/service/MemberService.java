package ch.zli.m223.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import ch.zli.m223.model.Member;

@ApplicationScoped
public class MemberService {

    @Inject
    EntityManager entityManager;

    public List<Member> getAllMembers() {
        return entityManager.createQuery("FROM Member", Member.class).getResultList();
    }

    @Transactional
    public Member createMember(Member member) {
        return entityManager.merge(member);
    }

    @Transactional
    public Member updateMember(long memberId, Member member) {
        member.setMemberId(memberId);
        Member updatedMember = entityManager.merge(member);
        
        if (updatedMember == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            return updatedMember;
        }
    }

    @Transactional
    public void changeActiveState(long memberId, boolean activeState) {
        int updatedMembers = entityManager.createQuery(
                "UPDATE Member m SET m.isActive = :activeState WHERE m.memberId = :memberId")
                .setParameter("memberId", memberId)
                .setParameter("activeState", activeState)
                .executeUpdate();

        if (updatedMembers == 0) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Transactional
    public void changeRole(long memberId, String role) {
        Object selectedRole = entityManager.createQuery("SELECT r FROM Role r WHERE r.title = :role ")
                .setParameter("role", role)
                .getSingleResult();

        int updatedMembers = entityManager.createQuery(
                "UPDATE Member m SET m.role = :role WHERE m.memberId = :memberId")
                .setParameter("role", selectedRole)
                .setParameter("memberId", memberId)
                .executeUpdate();

        if (updatedMembers == 0) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    public Optional<Member> findByEmail(String email) {
        return entityManager.createQuery("SELECT m FROM Member m WHERE m.emailAddress = :email", Member.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

}
