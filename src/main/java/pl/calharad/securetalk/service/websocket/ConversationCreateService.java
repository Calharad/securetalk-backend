package pl.calharad.securetalk.service.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.calharad.securetalk.dao.ConversationDao;
import pl.calharad.securetalk.dto.conversation.UserConversationTO;
import pl.calharad.securetalk.entity.Conversation;
import pl.calharad.securetalk.entity.User;
import pl.calharad.securetalk.websocket.WebsocketHandler;
import pl.calharad.securetalk.websocket.WebsocketMethod;
import pl.calharad.securetalk.websocket.data.WebsocketCommand;
import pl.calharad.securetalk.websocket.data.WebsocketMessage;
import pl.calharad.securetalk.websocket.data.input.NewConversationTO;
import pl.calharad.securetalk.websocket.data.output.NewConversationResponseTO;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.List;

@WebsocketMethod(WebsocketCommand.NEW_CONVERSATION)
@ApplicationScoped
public class ConversationCreateService implements WebsocketHandler {

    @Inject
    ConversationDao conversationDao;
    @Inject
    ObjectMapper mapper;
    @Inject
    @Any
    Event<NewConversationResponseTO> conversationEvent;

    @Override
    public void handle(WebsocketMessage message, User user) {
        NewConversationTO data = mapper.convertValue(message.getData(), NewConversationTO.class);
        Conversation conv = new Conversation();
        conv.setUpdateDate(message.getTimestamp());
        conv.setCreator(user);
        conv.setOwner(user);
        conv.setConversationName(data.getConversationName());
        conv.addMember(user);
        conversationDao.flushSave(conv);
        NewConversationResponseTO res = new NewConversationResponseTO();
        res.setConversationName(data.getConversationName());
        res.setId(conv.getId());
        res.setMembers(List.of(new UserConversationTO(user)));
        conversationEvent.fireAsync(res);
    }
}
