package ru.fmeter.edu.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.edu.service.TokenService;

import java.util.Collection;

@Service
public class TokenAuthenticationManager implements AuthenticationManager {
    private final UserService userService;
    private final TokenService tokenService;

    public TokenAuthenticationManager(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            if (authentication instanceof TokenAuthentication) {
                String token = ((TokenAuthentication) authentication).getToken();
                User user = (User) userService.loadUserByUsername(tokenService.parseLogin(token));
                if (user.isEnabled()) {
                    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
                    return new TokenAuthentication(token, authorities, true, user);
                } else throw new AuthenticationServiceException("User disabled");

            } else {
                authentication.setAuthenticated(false);
                return authentication;
            }
        } catch (Exception e) {
            if (e instanceof AuthenticationServiceException) {
                throw e;
            }
            return null;
        }
    }
}
