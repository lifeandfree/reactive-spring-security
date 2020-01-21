package ru.innopolis.service.sec.service.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.innopolis.model.User;
import ru.innopolis.model.UserStatus;

import java.util.ArrayList;
import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = -7378672680413606606L;
    private User userSession;
    private Collection<GrantedAuthority> authorities;

    public UserPrincipal(User userSession) {
        this.userSession = userSession;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        authorities = new ArrayList();
        authorities.add(new GrantedAuthority() {

            private static final long serialVersionUID = -3409244047754593097L;

            @Override
            public String getAuthority() {
                return userSession.getRole().name();
            }
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return userSession.getPassword();
    }

    @Override
    public String getUsername() {
        return userSession.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userSession.getUserStatus().name().equals(UserStatus.STATUS_LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return  userSession.getUserStatus().name().equals(UserStatus.STATUS_ACTIVED);
    }

    public User getUserSession() {
        return userSession;
    }

    public void setUserSession(User userSession) {
        this.userSession = userSession;
    }
}
