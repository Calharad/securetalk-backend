package pl.calharad.securetalk.websocket;

import pl.calharad.securetalk.websocket.data.WebsocketCommand;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface WebsocketMethod {
    WebsocketCommand value();
}
