package pl.calharad.securetalk.websocket.data.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.calharad.securetalk.entity.Message;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponseTO implements Encodable {
    public MessageResponseTO(Message message) {
        conversationId = message.getConversation().getId();
        content = message.getContent();
        userId = message.getCreator().getId();
        timestamp = message.getMessageDate();
    }

    private Long conversationId;
    private String content;
    private Integer userId;
    private OffsetDateTime timestamp;
}
