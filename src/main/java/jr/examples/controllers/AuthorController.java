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
import jr.examples.controllers.model.create.CreateAuthorRequest;
import jr.examples.controllers.model.update.UpdateAuthorRequest;
import jr.examples.services.AuthorService;

@Path("/api/v1/author")
public class AuthorController {
    @Inject
    ResponseUtils responseUtils;
    @Inject
    AuthorService authorService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createAuthor(CreateAuthorRequest authorRequest) {
        try {
            var authorDto = authorService.createAuthor(authorRequest);
            return Response.ok(authorDto).build();
        } catch (IllegalArgumentException e) {
            return responseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthors() {
        return Response.ok(authorService.getAllAuthors()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorById(@PathParam("id") Long id) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        var authorDto = authorService.getAuthorById(id);
        if (authorDto != null)
            return Response.ok(authorDto).build();
        return responseUtils.notFound("Author ID does not exist");
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateAuthor(@PathParam("id") Long id, UpdateAuthorRequest authorRequest) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        try {
            var updatedAuthorDto = authorService.updateAuthor(id, authorRequest);
            return Response.ok(updatedAuthorDto).build();
        } catch (NotFoundException e) {
            return responseUtils.notFound(e.getMessage());
        } catch (IllegalArgumentException e) {
            return responseUtils.badRequest(e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteAuthor(@PathParam("id") Long id) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        try {
            authorService.deleteAuthor(id);
        } catch (IllegalArgumentException e) {
            return responseUtils.notFound("Author ID does not exist");
        }

        return Response.ok().build();
    }
}
