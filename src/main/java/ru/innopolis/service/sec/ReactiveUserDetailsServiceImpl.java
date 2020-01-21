package ru.innopolis.service.sec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;
import ru.innopolis.dao.UserDao;
import ru.innopolis.model.User;
import ru.innopolis.service.sec.service.config.UserPrincipal;

//@Component
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private UserDao userDao;
    private static final Logger logger = LogManager.getLogger(ReactiveUserDetailsServiceImpl.class.getName());

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        User user = userDao.findUserByLoginOrEmail(username);

        if (user == null) {
            logger.error("Can't find user with username: \"" + username + "\"");
            throw new UsernameNotFoundException("Неверный логин или пароль");
        }

        return Mono.just(new UserPrincipal(user));
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
