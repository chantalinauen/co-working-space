package ch.zli.m223.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

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
        return entityManager.merge(member);
    }

    @Transactional
    public void changeActiveState(long memberId, boolean activeState) {
        Query query = entityManager.createQuery(
                "UPDATE Member m SET m.isActive = :activeState WHERE m.memberId = :memberId");
        query.setParameter("memberId", memberId);
        query.setParameter("activeState", activeState);
        query.executeUpdate();
    }

    @Transactional
    public void changeRole(long memberId, String role) {
        Query selectRoleQuery = entityManager.createQuery("SELECT r FROM Role r WHERE r.title = :role ");
        Object selectedRole = selectRoleQuery.setParameter("role", role).getSingleResult();

        Query query = entityManager.createQuery(
                "UPDATE Member m SET m.role = :role WHERE m.memberId = :memberId");
        query.setParameter("role", selectedRole);
        query.setParameter("memberId", memberId);
        query.executeUpdate();
    }

    public Optional<Member> findByEmail(String email) {
        return entityManager.createQuery("SELECT m FROM Member m WHERE m.emailAddress = :email", Member.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

}
