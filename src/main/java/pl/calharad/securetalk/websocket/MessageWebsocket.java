package pl.calharad.securetalk.websocket;

import io.quarkus.security.Authenticated;
import pl.calharad.securetalk.dao.UserDao;
import pl.calharad.securetalk.entity.User;
import pl.calharad.securetalk.websocket.data.WebsocketMessage;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@ServerEndpoint(
        value = "/chat",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class}
)
@Authenticated
public class MessageWebsocket {

    private static final Map<Integer, List<Session>> userSessions = new ConcurrentHashMap<>();
    private static final Map<Integer, List<Integer>> conversationUsers = new ConcurrentHashMap<>();

    @Inject
    UserDao userDao;

    @Inject
    @Any
    Instance<WebsocketHandler> handlerInstance;

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
    public void onError(Session session) throws Exception {
        User user = getUserOrThrow(session);
        userSessions.getOrDefault(user.getId(), new ArrayList<>()).remove(session);
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
