package pl.calharad.securetalk.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Max(256)
    private String conversationName;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", updatable = false, nullable = false)
    private User creator;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "conversation_members",
            joinColumns = @JoinColumn(name = "conversation_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    @Setter(AccessLevel.PRIVATE)
    private Set<User> members = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conversation")
    @Setter(AccessLevel.PRIVATE)
    private Set<Message> messages;

    private OffsetDateTime updateDate;

    public void addMember(User user) {
        this.members.add(user);
        user.getConversations().add(this);
    }

    public void addMessage(Message message) {
        message.setConversation(this);
        getMessages().add(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conversation)) return false;

        Conversation conv = (Conversation) o;

        return id != null && id.equals(conv.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
