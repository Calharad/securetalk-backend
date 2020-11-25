package pl.calharad.securetalk.service;

import pl.calharad.securetalk.dao.UserDao;
import pl.calharad.securetalk.dto.user.LoginUserTO;
import pl.calharad.securetalk.dto.user.RegisterUserTO;
import pl.calharad.securetalk.entity.User;
import pl.calharad.securetalk.exception.IncorrectPasswordException;
import pl.calharad.securetalk.security.PasswordEncoder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@RequestScoped
public class UserService {
    @Inject
    EntityManager em;
    @Inject
    PasswordEncoder encoder;
    @Inject
    UserDao userDao;

    @Transactional
    public void register(RegisterUserTO newUser) {
        Optional<User> existingUser = userDao.getUserByUsername(newUser.getUsername());
        if(existingUser.isPresent()) {
            throw new EntityExistsException("User exists");
        }
        User user = new User();
        user.setPassword(newUser.getPassword(), encoder);
        user.setUsername(newUser.getUsername());
        em.persist(user);
    }

    @Transactional
    public User loginUser(LoginUserTO user) {
        User dbUser = userDao.getUserByUsername(user.getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        boolean loggedIn = encoder.equals(user.getPassword(), dbUser.getPassword(), dbUser.getSalt());
        if (!loggedIn) {
            throw new IncorrectPasswordException("Incorrect password");
        }
        return dbUser;
    }
}
