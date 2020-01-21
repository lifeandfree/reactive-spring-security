package ru.innopolis.service.sec.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.innopolis.service.sec.service.config.UserPrincipal;
import ru.innopolis.service.sec.session.SpringWebSessionConfig;

import java.net.URI;

public class ServerAuthenticationSuccessHandlerImpl implements ServerAuthenticationSuccessHandler {

    private SpringWebSessionConfig springWebSessionConfig;
    private URI location = URI.create("/");
    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    private ServerRequestCache requestCache = new WebSessionServerRequestCache();

    public ServerAuthenticationSuccessHandlerImpl() {}

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange,
                                              Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!springWebSessionConfig.getAuthenticationConcurrentHashMap().containsKey(userPrincipal.getUserSession().getId())) {
            springWebSessionConfig.getAuthenticationConcurrentHashMap().put(userPrincipal.getUserSession().getId(), authentication);
        }

        return this.requestCache.getRedirectUri(exchange)
                .defaultIfEmpty(this.location)
                .flatMap(location -> this.redirectStrategy.sendRedirect(exchange, location));
    }

    @Autowired
    public void setSpringWebSessionConfig(SpringWebSessionConfig springWebSessionConfig) {
        this.springWebSessionConfig = springWebSessionConfig;
    }
}
