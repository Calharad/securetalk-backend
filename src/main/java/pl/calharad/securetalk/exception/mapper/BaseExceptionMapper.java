package pl.calharad.securetalk.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.calharad.securetalk.base.ExceptionTO;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(50)
public class BaseExceptionMapper implements ExceptionMapper<Exception> {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public Response toResponse(Exception e) {
        LOG.error("Nieobsługiwany wyjątek", e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(
                        new ExceptionTO("Server error")
                )
                .build();
    }
}
