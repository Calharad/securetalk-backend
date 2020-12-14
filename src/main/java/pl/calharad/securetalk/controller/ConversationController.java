package pl.calharad.securetalk.controller;

import io.quarkus.security.Authenticated;
import pl.calharad.securetalk.dto.conversation.ConversationTO;
import pl.calharad.securetalk.entity.User;
import pl.calharad.securetalk.service.ConversationService;
import pl.calharad.securetalk.service.UserService;
import pl.calharad.securetalk.utils.page.Page;
import pl.calharad.securetalk.utils.page.PageParams;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.Principal;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("conversation")
@Authenticated
public class ConversationController {

    @Inject
    Principal principal;
    @Inject
    ConversationService conversationService;
    @Inject
    UserService userService;

    @GET
    @Path("latest")
    public Page<ConversationTO> getLatestConversations(@BeanParam PageParams params) {
        User user = userService.getUserFromPrincipal(principal);
        return conversationService.getLatestConversationsForUser(user, params);
    }
}
