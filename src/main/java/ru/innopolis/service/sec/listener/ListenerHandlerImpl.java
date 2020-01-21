package ru.innopolis.service.sec.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.innopolis.model.User;
import ru.innopolis.service.sec.service.config.UserPrincipal;
import ru.innopolis.service.sec.service.interfaces.ListenerHandler;
import ru.innopolis.service.sec.session.SpringWebSessionConfig;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ListenerHandlerImpl implements ListenerHandler {

    private SpringWebSessionConfig springWebSessionConfig;

    @Override
    public void updateAuthentication(User user) {
        ConcurrentHashMap<UUID, Authentication> authenticationConcurrentHashMap =
                springWebSessionConfig.getAuthenticationConcurrentHashMap();
        if (authenticationConcurrentHashMap.containsKey(user.getId())) {
            Authentication authentication = authenticationConcurrentHashMap.get(user.getId());
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            userPrincipal.setUserSession(user);
        }
    }

    @Autowired
    public void setSpringWebSessionConfig(SpringWebSessionConfig springWebSessionConfig) {
        this.springWebSessionConfig = springWebSessionConfig;
    }
}
