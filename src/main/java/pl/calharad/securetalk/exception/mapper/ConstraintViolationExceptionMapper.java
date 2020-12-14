package pl.calharad.securetalk.exception.mapper;

import pl.calharad.securetalk.base.ExceptionTO;

import javax.annotation.Priority;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(10)
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(
                        new ExceptionTO("Server error")
                )
                .build();
    }
}
