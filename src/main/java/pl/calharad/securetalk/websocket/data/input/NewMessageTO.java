package pl.calharad.securetalk.websocket.data.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewMessageTO {
    private Long conversationId;
    private String message;
}
