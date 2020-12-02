package pl.calharad.securetalk.service;

import pl.calharad.securetalk.entity.User;
import pl.calharad.securetalk.websocket.WebsocketHandler;
import pl.calharad.securetalk.websocket.WebsocketMethod;
import pl.calharad.securetalk.websocket.data.WebsocketCommand;
import pl.calharad.securetalk.websocket.data.WebsocketMessage;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
@WebsocketMethod(WebsocketCommand.NEW_MESSAGE)
public class MessageService implements WebsocketHandler {

    @Override
    public void handle(WebsocketMessage message, User user) {

    }
}
