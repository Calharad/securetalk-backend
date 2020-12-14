package pl.calharad.securetalk.service;

import pl.calharad.securetalk.dao.ConversationDao;
import pl.calharad.securetalk.dto.conversation.ConversationTO;
import pl.calharad.securetalk.entity.Conversation;
import pl.calharad.securetalk.entity.User;
import pl.calharad.securetalk.utils.page.Page;
import pl.calharad.securetalk.utils.page.PageParams;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class ConversationService {
    @Inject
    ConversationDao conversationDao;

    public Page<ConversationTO> getLatestConversationsForUser(User user, PageParams params) {
        Page<Conversation> conversations = conversationDao.getLatestConversationsPageForUser(user, params);
        return conversations.map(this::toConversationTO);
    }

    private ConversationTO toConversationTO(Conversation conv) {
        ConversationTO dto = new ConversationTO();
        dto.setId(conv.getId());
        if(Objects.isNull(conv.getConversationName())) {
            String convName = conv.getMembers()
                    .stream().map(User::getUsername)
                    .collect(Collectors.joining(", "));
            dto.setConversationName(convName);
        } else {
            dto.setConversationName(conv.getConversationName());
        }
        dto.setUpdateDate(conv.getUpdateDate());
        return dto;
    }
}
