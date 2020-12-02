package pl.calharad.securetalk.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import pl.calharad.securetalk.websocket.data.WebsocketMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<WebsocketMessage> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String encode(WebsocketMessage o) throws EncodeException {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new EncodeException(o, "Unable to encode", e);
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
