package ru.innopolis.service.sec.session;

import static org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.session.MapSession;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.Session;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReactiveMapSessionRepositoryImpl extends ReactiveMapSessionRepository implements ReactiveSessionRepository<MapSession> {

    private ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> sessionsByAuth;

    public ReactiveMapSessionRepositoryImpl(Map<String, Session> sessions,
                                        ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> sessionsByAuth) {
        super(sessions);
        this.sessions = sessions;
        this.sessionsByAuth = sessionsByAuth;
    }

    private final Map<String, Session> sessions;

    @Override
    public Mono<Void> save(MapSession session) {
        return Mono.fromRunnable(() -> {
            if (!session.getId().equals(session.getOriginalId())) {
                removeFromSessionStore(session.getOriginalId());
                this.sessions.remove(session.getOriginalId());
            }
            this.sessions.put(session.getId(), new MapSession(session));
            SecurityContext securityContext = session.getAttribute(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);
            if (securityContext != null) {
                ConcurrentHashMap<String, Session> sessions = sessionsByAuth.get(securityContext.getAuthentication().getName());
                if (sessions == null){
                    sessions = new ConcurrentHashMap<>();
                    sessionsByAuth.put(securityContext.getAuthentication().getName(), sessions);
                }
                sessions.put(session.getId(), session);
            }
        });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        removeFromSessionStore(id);
        return Mono.fromRunnable(() -> this.sessions.remove(id));
    }

    private void removeFromSessionStore(String id) {
        Session session = sessions.get(id);
        if (session != null) {
            SecurityContext securityContext = session.getAttribute(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);
            if (securityContext != null) {
                ConcurrentHashMap<String, Session> sessionsFromSC = sessionsByAuth.get(securityContext.getAuthentication().getName());
                if (sessionsFromSC != null) {
                    sessionsFromSC.remove(session.getId());
                }
            }
        }
    }

}
