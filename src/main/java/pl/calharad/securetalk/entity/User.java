package pl.calharad.securetalk.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import pl.calharad.securetalk.security.PasswordEncoder;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Setter(AccessLevel.NONE)
    private byte[] password;

    @Column(nullable = false, updatable = false)
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Setter(AccessLevel.NONE)
    private byte[] salt;

    public void setPassword(String password, PasswordEncoder encoder) {
        this.salt = encoder.generateSalt();
        this.password = encoder.encode(password, this.salt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
