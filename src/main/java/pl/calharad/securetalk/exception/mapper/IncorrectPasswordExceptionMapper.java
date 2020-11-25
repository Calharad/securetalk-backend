package pl.calharad.securetalk.exception.mapper;

import pl.calharad.securetalk.base.ExceptionTO;
import pl.calharad.securetalk.base.ResponseTO;
import pl.calharad.securetalk.exception.IncorrectPasswordException;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(10)
public class IncorrectPasswordExceptionMapper implements ExceptionMapper<IncorrectPasswordException> {
    @Override
    public Response toResponse(IncorrectPasswordException e) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(ResponseTO.builder()
                        .success(false)
                        .exception(new ExceptionTO(e.getMessage()))
                        .build()
                ).build();
    }
}
