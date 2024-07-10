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
import jr.examples.controllers.model.create.CreateBookRequest;
import jr.examples.controllers.model.update.UpdateBookRequest;
import jr.examples.services.BookService;

@Path("/api/v1/book")
public class BookController {
    @Inject
    ResponseUtils responseUtils;
    @Inject
    BookService bookService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createBook(CreateBookRequest bookRequest) {
        try {
            var bookDto = bookService.createBook(bookRequest);
            return Response.ok(bookDto).build();
        } catch (IllegalArgumentException e) {
            return responseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        return Response.ok(bookService.getAllBooks()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") Long id) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        var bookDto = bookService.getBookById(id);
        if (bookDto != null)
            return Response.ok(bookDto).build();
        return responseUtils.notFound("Book ID does not exist");
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateBook(@PathParam("id") Long id, UpdateBookRequest bookRequest) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        try {
            var updatedBookDto = bookService.updateBook(id, bookRequest);
            return Response.ok(updatedBookDto).build();
        } catch (NotFoundException e) {
            return responseUtils.notFound(e.getMessage());
        } catch (IllegalArgumentException e) {
            return responseUtils.badRequest(e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteBook(@PathParam("id") Long id) {
        if (id == null)
            return responseUtils.badRequest("ID cannot be empty");

        try {
            bookService.deleteBook(id);
        } catch (IllegalArgumentException e) {
            return responseUtils.notFound("Book ID does not exist");
        }

        return Response.ok().build();
    }
}
