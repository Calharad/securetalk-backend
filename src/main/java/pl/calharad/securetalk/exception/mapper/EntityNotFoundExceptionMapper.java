package pl.calharad.securetalk.exception.mapper;

import pl.calharad.securetalk.base.ExceptionTO;

import javax.annotation.Priority;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(10)
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {
    @Override
    public Response toResponse(EntityNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(
                        new ExceptionTO("Server error")
                ).build();
    }
}
