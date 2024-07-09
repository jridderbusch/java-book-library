package jr.examples.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jr.examples.api.model.AuthorDto;
import jr.examples.api.model.BookDto;

@Path("/api/v1/book")
public class BookResource {

    @GET
    @Path("/{title}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getBook(@PathParam("title") String title) {
        if (title.isBlank()) {
            return badRequest("Title cannot be empty");
        } else {
            return Response.ok(new BookDto(title, "", 12.98, new AuthorDto())).build();
        }
    }

    private Response badRequest(String message) {
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
}
