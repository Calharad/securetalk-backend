package pl.calharad.securetalk.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginUserTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
