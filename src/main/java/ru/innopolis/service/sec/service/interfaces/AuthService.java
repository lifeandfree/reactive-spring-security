package ru.innopolis.service.sec.service.interfaces;

import ru.innopolis.model.User;
import ru.innopolis.service.sec.exception.AuthenticationServiceException;

import java.security.Principal;

/**
 * Интерфейс получения хранящихся пользователей в сессии.
 */
public interface AuthService {


    /**
     * Получить пользователя хранящего в сессии
     *
     * @return
     * @throws AuthenticationServiceException
     */
    User getActiveUserSession(Principal principal) throws AuthenticationServiceException;

    /**
     * Получить пользователя хранящего в сессии
     *
     * @return
     * @throws AuthenticationServiceException
     */
    User getUserSession(Principal principal) throws AuthenticationServiceException;
}
