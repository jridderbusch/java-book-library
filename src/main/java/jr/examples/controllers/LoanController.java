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
import jr.examples.controllers.model.create.CreateLoanRequest;
import jr.examples.controllers.model.update.UpdateLoanRequest;
import jr.examples.services.LoanService;

@Path("/api/v1/loan")
public class LoanController {
    @Inject
    ResponseUtils responseUtils;
    @Inject
    LoanService loanService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createLoan(CreateLoanRequest loanRequest) {
        try {
            var loanDto = loanService.createLoan(loanRequest);
            return Response.ok(loanDto).build();
        } catch (IllegalArgumentException e) {
            return responseUtils.badRequest(e.getMessage());
        } catch (IllegalStateException e) {
            return responseUtils.forbidden(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoans() {
        return Response.ok(loanService.getAllLoans()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoanById(@PathParam("id") Long id) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        var loanDto = loanService.getLoanById(id);
        if (loanDto != null)
            return Response.ok(loanDto).build();
        return responseUtils.notFound("Loan ID does not exist");
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateLoan(@PathParam("id") Long id, UpdateLoanRequest loanRequest) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        try {
            var updatedLoanDto = loanService.updateLoan(id, loanRequest);
            return Response.ok(updatedLoanDto).build();
        } catch (NotFoundException e) {
            return responseUtils.notFound(e.getMessage());
        } catch (IllegalArgumentException e) {
            return responseUtils.badRequest(e.getMessage());
        } catch (IllegalStateException e) {
            return responseUtils.forbidden(e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteLoan(@PathParam("id") Long id) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        try {
            loanService.deleteLoan(id);
        } catch (IllegalArgumentException e) {
            return responseUtils.notFound("Loan ID does not exist");
        }

        return Response.ok().build();
    }
}
