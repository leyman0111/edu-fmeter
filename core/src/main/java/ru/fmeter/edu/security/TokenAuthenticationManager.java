package ru.fmeter.edu.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.edu.service.TokenService;

@Component
public class TokenAuthenticationManager implements AuthenticationManager {
    private final TokenService tokenService;
    private final UserService userService;

    public TokenAuthenticationManager(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String userName = tokenService.validate((String) authentication.getPrincipal());
            if (userName == null) {
                authentication.setAuthenticated(false);
                return authentication;
            }
            User principal = (User) userService.loadUserByUsername(userName);
            return new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        } catch (Exception e) {
            if (e instanceof AuthenticationServiceException) {
                throw e;
            }
            return null;
        }
    }
}
