package pl.calharad.securetalk.websocket;

import io.quarkus.security.Authenticated;
import pl.calharad.securetalk.base.ExceptionTO;
import pl.calharad.securetalk.dao.UserDao;
import pl.calharad.securetalk.entity.User;
import pl.calharad.securetalk.websocket.data.WebsocketMessage;
import pl.calharad.securetalk.websocket.data.output.Encodable;
import pl.calharad.securetalk.websocket.data.output.MessageResponseTO;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ApplicationScoped
@ServerEndpoint(
        value = "/chat",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class}
)
@Authenticated
public class MessageWebsocket {

    private static final Map<Integer, List<Session>> userSessions = new ConcurrentHashMap<>();
    private static final Map<Long, List<Integer>> conversationUsers = new ConcurrentHashMap<>();

    @Inject
    UserDao userDao;

    @Inject
    @Any
    Instance<WebsocketHandler> handlerInstance;

    void sendMessageToUsers(Encodable message, List<Integer> users) {
        List<Session> sessions = users.stream()
                .map(u -> userSessions.getOrDefault(u, List.of()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        sendMessage(message, sessions);
    }

    void sendMessage(Encodable message, List<Session> sessions) {
        for(Session session: sessions) {
            session.getAsyncRemote().sendObject(message);
        }
    }

    public void sendMessageToUser(Encodable message, Integer user) {
        List<Session> sessions = userSessions.getOrDefault(user, List.of());
        sendMessage(message, sessions);
    }

    void sendMessageToConversation(Encodable message, Long conversation) {
        sendMessageToUsers(message, conversationUsers.getOrDefault(conversation, List.of()));
    }

    void onNewMessage(@Observes MessageResponseTO response) {
        sendMessageToConversation(response, response.getConversationId());
    }

    @OnOpen
    public void onOpen(Session session) throws Exception {
        User user = getUserOrThrow(session);
        userSessions.computeIfAbsent(user.getId(), ArrayList::new).add(session);
    }

    @OnMessage
    public void onMessage(Session session, WebsocketMessage message) throws Exception {
        User user = getUserOrThrow(session);
        WebsocketMethodLiteral literal = new WebsocketMethodLiteral(message.getCommand());
        Instance<WebsocketHandler> instance = handlerInstance.select(literal);
        if (instance.isAmbiguous() || instance.isUnsatisfied()) {
            throw new IllegalStateException("Method not found");
        }
        instance.get().handle(message, user);
    }

    @OnError
    public void onError(Session session, Throwable exc) {
        session.getAsyncRemote().sendObject(exc.getMessage());
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        User user = getUserOrThrow(session);
        userSessions.getOrDefault(user.getId(), new ArrayList<>()).remove(session);
    }

    private User getUserOrThrow(Session session) throws Exception {
        String username = session.getUserPrincipal().getName();
        Optional<User> optionalUser = userDao.getUserByUsername(username);
        if(optionalUser.isEmpty()) {
            throw new Exception();
        }
        return optionalUser.get();
    }
}
