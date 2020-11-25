package pl.calharad.securetalk.interceptor;

import pl.calharad.securetalk.base.ResponseTO;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;

@Provider
public class BoxingWriterInterceptor implements WriterInterceptor {
    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        if(!context.getEntity().getClass().equals(ResponseTO.class)) {
            Object obj = context.getEntity();
            ResponseTO res = ResponseTO.builder().success(Boolean.TRUE)
                    .data(obj)
                    .build();
            context.setEntity(res);
            context.setType(ResponseTO.class);
            context.setGenericType(ResponseTO.class);
        }
        context.proceed();
    }
}
