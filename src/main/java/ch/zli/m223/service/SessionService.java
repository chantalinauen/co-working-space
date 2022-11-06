package ch.zli.m223.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import ch.zli.m223.model.Credentials;
import ch.zli.m223.model.Member;
import ch.zli.m223.model.Role;
import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class SessionService {

  @Inject
  MemberService memberService;

  public Response authenticate(Credentials credentials) {
    Optional<Member> principal = memberService.findByEmail(credentials.getEmail());

    try {
      if (principal.isPresent() && principal.get().getPassword().equals(credentials.getPassword())) {
        String token = Jwt
            .issuer("https://zli.example.com/")
            .upn(credentials.getEmail())
            .groups(new HashSet<>(Arrays.asList(Role.MEMBER, Role.ADMIN)))
            .expiresIn(Duration.ofHours(24))
            .sign();
        return Response
            .ok(principal.get())
            .header("Authorization", "Bearer " + token)
            .build();
      }
    } catch (Exception e) {
      System.err.println("Could not validate password.");
    }

    return Response.status(Response.Status.FORBIDDEN).build();
  }

}
