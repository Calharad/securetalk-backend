package pl.calharad.securetalk.base;

import lombok.*;
import pl.calharad.securetalk.websocket.data.output.Encodable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionTO implements Encodable {
    private String message;
}
