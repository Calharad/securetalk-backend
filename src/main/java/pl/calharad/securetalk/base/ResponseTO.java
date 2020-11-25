package pl.calharad.securetalk.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTO {
    @Builder.Default
    private boolean success = true;
    @Builder.Default
    private ExceptionTO exception = null;
    @Builder.Default
    private Object data = null;
}
