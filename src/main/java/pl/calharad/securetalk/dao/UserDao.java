package pl.calharad.securetalk.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import pl.calharad.securetalk.entity.QUser;
import pl.calharad.securetalk.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@RequestScoped
@Transactional
public class UserDao {
    @Inject
    EntityManager em;

    public Optional<User> getUserByUsername(String username) {
        JPAQueryFactory qf = new JPAQueryFactory(em);
        QUser qUser = QUser.user;
        return Optional.ofNullable(
                qf.selectFrom(qUser)
                        .where(qUser.username.eq(username))
                        .fetchOne()
        );
    }
}
