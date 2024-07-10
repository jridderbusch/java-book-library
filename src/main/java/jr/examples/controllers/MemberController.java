package jr.examples.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jr.examples.controllers.model.create.CreateMemberRequest;
import jr.examples.controllers.model.update.UpdateMemberRequest;
import jr.examples.services.MemberService;

@Path("/api/v1/member")
public class MemberController {
    @Inject
    ResponseUtils responseUtils;
    @Inject
    MemberService memberService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createMember(CreateMemberRequest memberRequest) {
        try {
            var memberDto = memberService.createMember(memberRequest);
            return Response.ok(memberDto).build();
        } catch (IllegalArgumentException e) {
            return responseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMembers() {
        return Response.ok(memberService.getAllMembers()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberById(@PathParam("id") Long id) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        var memberDto = memberService.getMemberById(id);
        if (memberDto != null)
            return Response.ok(memberDto).build();
        return responseUtils.notFound("Member ID does not exist");
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateMember(@PathParam("id") Long id, UpdateMemberRequest memberRequest) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        try {
            var updatedMemberDto = memberService.updateMember(id, memberRequest);
            return Response.ok(updatedMemberDto).build();
        } catch (NotFoundException e) {
            return responseUtils.notFound(e.getMessage());
        } catch (IllegalArgumentException e) {
            return responseUtils.badRequest(e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteMember(@PathParam("id") Long id) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        try {
            memberService.deleteMember(id);
        } catch (IllegalArgumentException e) {
            return responseUtils.notFound("Member ID does not exist");
        }

        return Response.ok().build();
    }
}
