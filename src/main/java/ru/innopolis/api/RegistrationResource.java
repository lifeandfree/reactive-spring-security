package ru.innopolis.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;
import ru.innopolis.service.sec.service.dto.UserReqForm;

import java.security.Principal;

/**
 * RegistrationApi.
 *
 * @author Ilya_Sukhachev
 */
@RequestMapping("/api/registration")
@Api(value = "registration")
public interface RegistrationResource {

    @PostMapping
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(
        value = "Создание оператора",
        notes = "API для создания пользователя с ролью Operator",
        code = 201,
        httpMethod = "POST",
        consumes = "application/json",
        produces = "application/json",
        response = ru.innopolis.service.sec.service.dto.UserReqForm.class
        )
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 201, message = "Created", response = ru.innopolis.service.sec.service.dto.UserReqForm.class)
        })
    Mono<ResponseEntity<UserReqForm>> createOperator(
            @ApiParam(value = "Данные о пользователе, который регистритрируется", required = true)
            @RequestBody UserReqForm userReqForm,
            Principal principal);

    @PostMapping
    @RequestMapping(method = RequestMethod.POST, value = "/admin")
    @ResponseBody
    @ApiOperation(
        value = "Создание администратора",
        notes = "API для создания пользователя с ролью Admin",
        code = 201,
        httpMethod = "POST",
        consumes = "application/json",
        produces = "application/json",
        response = UserReqForm.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 201, message = "Created", response = UserReqForm.class)
        })
    Mono<ResponseEntity<UserReqForm>> createAdmin(
            @ApiParam(value = "Данные о пользователе, который регистритрируется", required = true)
            @RequestBody UserReqForm userReqForm);
}
