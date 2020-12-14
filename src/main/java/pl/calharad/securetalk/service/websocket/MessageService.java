package pl.calharad.securetalk.service.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.calharad.securetalk.dao.ConversationDao;
import pl.calharad.securetalk.dao.MessageDao;
import pl.calharad.securetalk.entity.Conversation;
import pl.calharad.securetalk.entity.Message;
import pl.calharad.securetalk.entity.User;
import pl.calharad.securetalk.websocket.WebsocketHandler;
import pl.calharad.securetalk.websocket.WebsocketMethod;
import pl.calharad.securetalk.websocket.data.WebsocketCommand;
import pl.calharad.securetalk.websocket.data.WebsocketMessage;
import pl.calharad.securetalk.websocket.data.input.NewMessageTO;
import pl.calharad.securetalk.websocket.data.output.MessageResponseTO;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@ApplicationScoped
@WebsocketMethod(WebsocketCommand.NEW_MESSAGE)
public class MessageService implements WebsocketHandler {

    @Inject
    ObjectMapper mapper;
    @Inject
    @Any
    Event<MessageResponseTO> messageEvent;
    @Inject
    ConversationDao conversationDao;
    @Inject
    MessageDao messageDao;

    @Override
    public void handle(WebsocketMessage message, User user) {
        NewMessageTO newMessage = mapper.convertValue(message.getData(), NewMessageTO.class);
        Conversation conv = conversationDao.getOne(newMessage.getConversationId());
        Message mess = new Message();
        mess.setContent(newMessage.getMessage());
        mess.setCreator(user);
        mess.setMessageDate(message.getTimestamp());
        conv.addMessage(mess);
        messageDao.save(mess);
        messageEvent.fire(new MessageResponseTO(mess));
    }
}
