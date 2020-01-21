package ru.innopolis.service.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import ru.innopolis.controller.handler.CommonExceptionHandler;
import ru.innopolis.service.sec.handler.ServerAuthenticationSuccessHandlerImpl;
import ru.innopolis.service.sec.handler.ServerLogoutSuccessHandlerImpl;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private ServerSecurityContextRepository serverSecurityContextRepository;
    private CommonExceptionHandler commonExceptionHandler;

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable().
                httpBasic().disable().
                authenticationManager(reactiveAuthenticationManager()).
                authorizeExchange()
                .pathMatchers("/api/registration/admin/**", "/api/login", "/api/logout", "/v2/api-docs",
                        "/swagger-resources/**", "/documentation/**", "/swagger-ui.html").permitAll()
                .anyExchange().authenticated()
                .and().formLogin().loginPage("/api/login")
                .authenticationFailureHandler(commonExceptionHandler)
                .authenticationSuccessHandler(serverAuthenticationSuccessHandler())
                .and().logout().logoutUrl("/api/logout").logoutSuccessHandler(serverLogoutSuccessHandler())
                .and().securityContextRepository(serverSecurityContextRepository)
                .exceptionHandling()
                .authenticationEntryPoint(commonExceptionHandler)
                .accessDeniedHandler(commonExceptionHandler)
                .and()
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return new ReactiveAuthenticationManagerImpl(reactiveUserDetailsService());
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        return new ReactiveUserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ServerAuthenticationSuccessHandler serverAuthenticationSuccessHandler() {
        return new ServerAuthenticationSuccessHandlerImpl();
    }

    @Bean
    public ServerLogoutSuccessHandler serverLogoutSuccessHandler() {
        return new ServerLogoutSuccessHandlerImpl();
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void setServerSecurityContextRepository(ServerSecurityContextRepository serverSecurityContextRepository) {
        this.serverSecurityContextRepository = serverSecurityContextRepository;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void setCommonExceptionHandler(CommonExceptionHandler commonExceptionHandler) {
        this.commonExceptionHandler = commonExceptionHandler;
    }
}




