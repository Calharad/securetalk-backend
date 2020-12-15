package pl.calharad.securetalk.dto.conversation;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class ConversationTO {
    private String conversationName;
    private Long id;
    private OffsetDateTime updateDate;
    private List<UserConversationTO> members;
}
