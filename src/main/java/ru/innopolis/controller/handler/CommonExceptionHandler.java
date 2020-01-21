package ru.innopolis.controller.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import ru.innopolis.controller.dto.ErrorDto;

@Component
public class CommonExceptionHandler implements WebExceptionHandler,
        ServerAuthenticationFailureHandler, ServerAccessDeniedHandler, ServerAuthenticationEntryPoint {

    private static final Logger logger = LogManager.getLogger(CommonExceptionHandler.class.getName());

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable e) {
        return getExceptionHandler(exchange, e);
    }

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        setResponseAttributes(webFilterExchange.getExchange().getResponse(), HttpStatus.FORBIDDEN);

        return webFilterExchange
                .getExchange()
                .getResponse()
                .writeWith(Mono.just(createErrorMessage(exception, "Ошибка авторизации")));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        setResponseAttributes(exchange.getResponse(), HttpStatus.FORBIDDEN);

        return exchange
                .getResponse()
                .writeWith(Mono.just(createErrorMessage(denied, "Доступ запрещен")));
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {

        setResponseAttributes(exchange.getResponse(), HttpStatus.UNAUTHORIZED);

        return exchange.getResponse().writeWith(Mono.just(createErrorMessage(e, "Вы не авторизованы")));
    }

    private Mono<Void> getExceptionHandler(ServerWebExchange exchange, Throwable e) {
        logger.error(e);

        if (e instanceof RuntimeException || e instanceof Exception)
            setResponseAttributes(exchange.getResponse(), HttpStatus.BAD_REQUEST);

        return exchange.getResponse().writeWith(Mono.just(createErrorMessage(e, "Неизвестная ошибка")));
    }

    private DataBuffer createErrorMessage(Throwable e, String message) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(), message == null ? e.getLocalizedMessage() : message);
        return getSerializeError(errorDto);
    }

    private DataBuffer getSerializeError(ErrorDto errorDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        DataBuffer db = null;
        try {
            db = new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(errorDto));
        } catch (JsonProcessingException ex) {
            logger.error(ex);
        }
        return db;
    }

    private void setResponseAttributes(ServerHttpResponse response, HttpStatus httpStatus) {
        response.setStatusCode(httpStatus);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
    }

}
