package ru.innopolis.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;
import ru.innopolis.controller.dto.UserInfoResponse;
import ru.innopolis.model.User;

import java.security.Principal;

/**
 * UserInfoResource.
 *
 * @author Ilya_Sukhachev
 */
@RequestMapping("/api/v1")
@Api(value = "userinfo")
public interface UserInfoResource {
    @GetMapping
    @RequestMapping(method = RequestMethod.GET, value = "/userinfo")
    @ResponseBody
    @ApiOperation(
        value = "Получить данные о пользователе",
        response = ru.innopolis.controller.dto.UserInfoResponse.class,
        notes = "API для запроса данных о пользователе")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ru.innopolis.controller.dto.UserInfoResponse.class) })
    Mono<ResponseEntity<User>> getUserInfo(Principal principal);
}
