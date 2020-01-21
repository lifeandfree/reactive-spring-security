package ru.innopolis.service.sec.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.session.Session;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import ru.innopolis.service.sec.service.config.UserPrincipal;
import ru.innopolis.service.sec.session.SpringWebSessionConfig;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

public class ServerLogoutSuccessHandlerImpl implements ServerLogoutSuccessHandler {

    public static final String DEFAULT_LOGOUT_SUCCESS_URL = "/login?logout";
    public static final String ANONYMOUS = "anonymous";
    private URI logoutSuccessUrl = URI.create(DEFAULT_LOGOUT_SUCCESS_URL);
    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    private SpringWebSessionConfig springWebSessionConfig;

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        if (!authentication.getPrincipal().equals(ANONYMOUS)) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> sessionsByAuth = springWebSessionConfig.getSessionsByAuth();
            ConcurrentHashMap<String, Session> sessionList = sessionsByAuth.get(userPrincipal.getUsername());
            if (sessionList != null && sessionList.size() == 0) {
                sessionsByAuth.remove(userPrincipal.getUsername());
                if (springWebSessionConfig.getAuthenticationConcurrentHashMap().containsKey(userPrincipal.getUserSession().getId())) {
                    springWebSessionConfig.getAuthenticationConcurrentHashMap().remove(userPrincipal.getUserSession().getId());
                    //TODO удалять, если закончились все сессии пользователя. Не всегда все сессии закрываются в Spring
                }
            }
        }
        return this.redirectStrategy
                .sendRedirect(exchange.getExchange(), this.logoutSuccessUrl);
    }

    public void setLogoutSuccessUrl(URI logoutSuccessUrl) {
        Assert.notNull(logoutSuccessUrl, "logoutSuccessUrl cannot be null");
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

    @Autowired
    public void setSpringWebSessionConfig(SpringWebSessionConfig springWebSessionConfig) {
        this.springWebSessionConfig = springWebSessionConfig;
    }
}
