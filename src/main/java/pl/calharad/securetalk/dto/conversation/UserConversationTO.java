package pl.calharad.securetalk.dto.conversation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.calharad.securetalk.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserConversationTO {

    public UserConversationTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    private Integer id;
    private String username;
}
