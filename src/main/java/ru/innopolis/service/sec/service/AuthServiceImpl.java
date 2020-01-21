package ru.innopolis.service.sec.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.innopolis.model.User;
import ru.innopolis.model.UserStatus;
import ru.innopolis.service.sec.exception.AuthenticationServiceException;
import ru.innopolis.service.sec.service.config.UserPrincipal;
import ru.innopolis.service.sec.service.interfaces.AuthService;

import java.security.Principal;

@Component
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class.getName());

    /**
     * Получить активного пользователя хранящего в сессии
     *
     * @param principal
     *
     * @return
     *
     * @throws AuthenticationServiceException
     */
    @Override
    public User getActiveUserSession(Principal principal) throws AuthenticationServiceException {
        User user = getUserFromSession(principal);

        if (user.getUserStatus().equals(UserStatus.STATUS_LOCKED)) {
            throw new AuthenticationServiceException("Данный пользователь заблокирован. Для выполнения данной операции требуется активный пользователь. Обратитесь в техподдержку.");
        }

        if (user.getUserStatus().equals(UserStatus.STATUS_CREATED)) {
            throw new AuthenticationServiceException("Вы не можете выполнить данную операцию. Данный пользователь еще не активирован");
        }

        return user;
    }

    /**
     * Получить пользователя хранящего в сессии
     *
     * @param principal
     *
     * @return
     *
     * @throws AuthenticationServiceException
     */
    @Override
    public User getUserSession(Principal principal) throws AuthenticationServiceException {
        User user = getUserFromSession(principal);
        return user;
    }

    /**
     * Получить пользователя хранящего в сессии
     *
     * @param principal
     *
     * @return
     *
     * @throws AuthenticationServiceException
     */
    public User getUserFromSession(Principal principal) throws AuthenticationServiceException {
        if (principal == null) {
            throw new AuthenticationServiceException("Пользователь не авторизован");
        }

        Authentication authentication = (Authentication) principal;
        if (authentication == null) {
            throw new AuthenticationServiceException("Пользователь не авторизован");
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new AuthenticationServiceException("Пользователь не авторизован");
        }

        User user = userPrincipal.getUserSession();
        if (user == null) {
            throw new AuthenticationServiceException("Пользователь не авторизован");
        }

        return user;
    }

}
