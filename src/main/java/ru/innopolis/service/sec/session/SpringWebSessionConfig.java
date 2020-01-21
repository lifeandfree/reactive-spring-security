package ru.innopolis.service.sec.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableSpringWebSession
public class SpringWebSessionConfig {

    private ConcurrentHashMap<String, Session> sessions;
    private ConcurrentHashMap<UUID, Authentication> authenticationConcurrentHashMap;
    private ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> sessionsByAuth;

    @Bean
    public ReactiveSessionRepository reactiveSessionRepository() {
        sessions = new ConcurrentHashMap<>();
        sessionsByAuth = new ConcurrentHashMap<>();
        authenticationConcurrentHashMap = new ConcurrentHashMap<>();
        ReactiveMapSessionRepository reactiveMapSessionRepository = new ReactiveMapSessionRepositoryImpl(sessions, sessionsByAuth);
        reactiveMapSessionRepository.setDefaultMaxInactiveInterval(3600);
        return reactiveMapSessionRepository;
    }

    @Bean
    public WebSessionServerSecurityContextRepository securityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }

    public ConcurrentHashMap<String, Session> getSessions() {
        return sessions;
    }

    public ConcurrentHashMap<UUID, Authentication> getAuthenticationConcurrentHashMap() {
        return authenticationConcurrentHashMap;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> getSessionsByAuth() {
        return sessionsByAuth;
    }
}
