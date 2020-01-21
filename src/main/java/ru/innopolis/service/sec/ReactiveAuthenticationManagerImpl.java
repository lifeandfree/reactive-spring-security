package ru.innopolis.service.sec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.innopolis.dao.OrganizationDAO;
import ru.innopolis.dao.UserDao;
import ru.innopolis.model.Organization;
import ru.innopolis.model.OrganizationStatus;
import ru.innopolis.model.User;
import ru.innopolis.service.sec.exception.NotUserOrPasswordAuthenticationException;
import ru.innopolis.service.sec.exception.OrganizationNotActiveAuthenticationException;
import ru.innopolis.service.sec.service.config.UserPrincipal;

public class ReactiveAuthenticationManagerImpl implements ReactiveAuthenticationManager {

    private static final Logger logger = LogManager.getLogger(ReactiveAuthenticationManagerImpl.class.getName());
    private ReactiveUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private UserDao userDao;
    private OrganizationDAO organizationDAO;

    @Autowired
    public ReactiveAuthenticationManagerImpl(ReactiveUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final String username = authentication.getName();
        return this.userDetailsService.findByUsername(username).
                publishOn(Schedulers.parallel()).
                filter(u -> {
                    if (this.passwordEncoder.matches((String) authentication.getCredentials(), u.getPassword())) {
                        User user = ((UserPrincipal) u).getUserSession();
                        Organization organization = organizationDAO.getByUser(user);
                        if (organization.getOrganizationStatus().equals(OrganizationStatus.STATUS_CREATED)) {
                            throw new OrganizationNotActiveAuthenticationException("Организация пользователя не активна на данный момент");
                        }
                        if (organization.getOrganizationStatus().equals(OrganizationStatus.STATUS_LOCKED)) {
                            throw new OrganizationNotActiveAuthenticationException("Организация пользователя заблокирована");
                        }
                        return true;
                    } else {
                        throw new NotUserOrPasswordAuthenticationException("Неверный логин или пароль");
                    }
                }).map(u -> new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities())
        );
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setOrganizationDAO(OrganizationDAO organizationDAO) {
        this.organizationDAO = organizationDAO;
    }
}
