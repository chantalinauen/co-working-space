package ch.zli.m223.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.Member;
import ch.zli.m223.service.MemberService;

@Path("/members")
@Tag(name = "Member", description = "Handling of members")
public class MemberController {

    @Inject
    MemberService memberService;

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lists all members.", description = "Returns a list of all registered members.")
    public List<Member> listMembers() {
        return memberService.getAllMembers();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new member.", description = "Creates a new member and returns the newly added member.")
    public Member createMember(@Valid Member member) {
        return memberService.createMember(member);
    }

    @Path("/{memberId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a member.", description = "Updates a member by its id.")
    public Member update(@PathParam("memberId") long id, @Valid Member member) {
        return memberService.updateMember(id, member);
    }

    @Path("/{memberId}")
    @DELETE
    @Operation(summary = "Deletes a member.", description = "Deletes a member by its id, respectivly set it to inactive. For better readability it's a DELETE request.")
    public void delete(@PathParam("memberId") long id) {
        memberService.changeActiveState(id, false);
    }

}
