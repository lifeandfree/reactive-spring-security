package ru.innopolis.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.innopolis.api.UserInfoResource;
import ru.innopolis.controller.dto.UserInfoResponse;
import ru.innopolis.model.User;
import ru.innopolis.service.sec.service.interfaces.AuthService;

import java.security.Principal;

@RestController
public class UserInfoResourceImpl  implements UserInfoResource {

    private static final Logger logger = LogManager.getLogger(UserInfoResourceImpl.class.getName());

    private AuthService authService;

    public UserInfoResourceImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Mono<ResponseEntity<User>> getUserInfo(Principal principal) {
        User userSession = authService.getUserSession(principal);
        logger.debug("Get User Info for user: " + userSession.getId());

        return Mono.just(new ResponseEntity<>(userSession, HttpStatus.OK));
    }
}
