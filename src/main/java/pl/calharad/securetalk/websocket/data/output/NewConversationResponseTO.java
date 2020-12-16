package pl.calharad.securetalk.websocket.data.output;

import lombok.Getter;
import lombok.Setter;
import pl.calharad.securetalk.dto.conversation.UserConversationTO;

import java.util.List;

@Getter
@Setter
public class NewConversationResponseTO implements Encodable {
    private String conversationName;
    private Long id;
    private List<UserConversationTO> members;
}
