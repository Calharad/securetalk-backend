package pl.calharad.securetalk.websocket;

import pl.calharad.securetalk.websocket.data.WebsocketCommand;

import javax.enterprise.util.AnnotationLiteral;

@SuppressWarnings("CdiInjectionPointsInspection")
class WebsocketMethodLiteral extends AnnotationLiteral<WebsocketMethod> implements WebsocketMethod {
    private final WebsocketCommand command;

    WebsocketMethodLiteral(WebsocketCommand command) {
        this.command = command;
    }

    @Override
    public WebsocketCommand value() {
        return command;
    }
}
