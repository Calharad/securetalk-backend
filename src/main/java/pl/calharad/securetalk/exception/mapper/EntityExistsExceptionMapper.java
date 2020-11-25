package pl.calharad.securetalk.exception.mapper;

import pl.calharad.securetalk.base.ExceptionTO;
import pl.calharad.securetalk.base.ResponseTO;

import javax.annotation.Priority;
import javax.persistence.EntityExistsException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(10)
public class EntityExistsExceptionMapper implements ExceptionMapper<EntityExistsException> {
    @Override
    public Response toResponse(EntityExistsException e) {
        return Response.status(Response.Status.CONFLICT)
                .entity(ResponseTO.builder()
                        .success(false)
                        .exception(new ExceptionTO(e.getMessage()))
                        .build()
                ).build();
    }
}
