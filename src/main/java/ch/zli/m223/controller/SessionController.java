package ch.zli.m223.controller;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.Credentials;
import ch.zli.m223.service.SessionService;


@Path("/session")
@Tag(name = "Session", description = "Handling of sessions")
@PermitAll
public class SessionController {

    @Inject
    SessionService sessionService;
  
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Authenticate a member.", description = "Returns a token upon successful authentication.")
    public Response createSession(@Valid Credentials credential) {
      return this.sessionService.authenticate(credential);
    }  
    
}
