package pl.calharad.securetalk.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import pl.calharad.securetalk.websocket.data.WebsocketMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<WebsocketMessage> {
    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    @Override
    public WebsocketMessage decode(String s) throws DecodeException {
        try {
            return mapper.readValue(s, WebsocketMessage.class);
        } catch (JsonProcessingException e) {
            throw new DecodeException(s, "Unable to decode", e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        return StringUtils.isNotBlank(s);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
