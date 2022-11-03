package ch.zli.m223.model;

import java.util.Set;

import javax.persistence.Entity;

@Entity
public class Role {
    
    private long roleId;

    private String title;

    private Set<Member> members;

}
