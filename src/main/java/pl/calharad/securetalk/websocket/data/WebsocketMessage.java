package pl.calharad.securetalk.websocket.data;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class WebsocketMessage {
    private JsonNode data;
    private WebsocketCommand command;
    private String from;
    private OffsetDateTime timestamp;
}
