package pl.calharad.securetalk.producers;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class ObjectMapperProducer {
    @Produces
    public ObjectMapper mapper() {
        return new ObjectMapper()
                .findAndRegisterModules();
    }
}
