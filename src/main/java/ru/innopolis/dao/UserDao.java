package ru.innopolis.dao;

import ru.innopolis.model.User;

/**
 * UserDao.
 *
 * @author Ilya_Sukhachev
 */
public interface UserDao {
    User findUserByLoginOrEmail(String username);

    void save(User user);
}
