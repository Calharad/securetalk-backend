package pl.calharad.securetalk.websocket;

import pl.calharad.securetalk.entity.User;
import pl.calharad.securetalk.websocket.data.WebsocketMessage;

public interface WebsocketHandler {
    void handle(WebsocketMessage message, User user);
}
