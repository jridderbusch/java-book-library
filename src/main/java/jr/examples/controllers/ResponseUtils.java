package jr.examples.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ResponseUtils {
    public Response badRequest(String message) {
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }

    public Response notFound(String message) {
        return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }
}
