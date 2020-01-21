package ru.innopolis.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.model.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UserDAOImpl.
 *
 * @author Ilya_Sukhachev
 */
@Repository
public class UserDAOImpl implements UserDao {

    Map<UUID, User> users = new ConcurrentHashMap();

    @Override
    public User findUserByLoginOrEmail(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID());
        users.put(user.getId(), user);
    }
}
