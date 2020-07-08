package ru.innopolis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.innopolis.api.RegistrationResource;
import ru.innopolis.model.User;
import ru.innopolis.service.sec.service.dto.UserReqForm;
import ru.innopolis.service.sec.service.interfaces.AuthService;
import ru.innopolis.service.sec.service.interfaces.RegistrationService;

import java.security.Principal;

@RestController
public class RegistrationResourceImpl implements RegistrationResource {

    private static final Logger logger = LogManager.getLogger(RegistrationResourceImpl.class.getName());

    private RegistrationService registrationService;
    private AuthService authService;

    public RegistrationResourceImpl(RegistrationService registrationService, AuthService authService) {
        this.registrationService = registrationService;
        this.authService = authService;
    }

    @Override
    public Mono<ResponseEntity<UserReqForm>> createOperator(
            @ApiParam(value = "Данные о пользователе, который регистритрируется", required = true)
            @RequestBody UserReqForm userReqForm,
            Principal principal) {
        User userSession = authService.getActiveUserSession(principal);
        logger.debug("Registering user account with information: ", userReqForm);

        return registrationService.registerNewOperatorAccount(userReqForm, userSession);
    }

    @Override
    public Mono<ResponseEntity<UserReqForm>> createAdmin(
            @ApiParam(value = "Данные о пользователе, который регистритрируется", required = true) 
            @RequestBody UserReqForm userReqForm) {
        logger.debug("Registering admin user account with information: ", userReqForm);

//        if (true)
//            new RuntimeException("RuntimeException");
        return registrationService.registerNewAdminAccount(userReqForm);
    }

}
