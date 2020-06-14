package ru.fmeter.edu.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.service.UserService;

import java.util.Collection;
import java.util.Date;

@Service
public class UserAuthenticationManager implements AuthenticationManager {
    private final UserService userService;

    public UserAuthenticationManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            if (authentication instanceof UserAuthentication) {
                return processAuthentication((UserAuthentication) authentication);
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

    private UserAuthentication processAuthentication(UserAuthentication authentication) throws AuthenticationException {
        String token = authentication.getToken();
        String key = "CmEyuNkONsSFyD8pryDdJisVvWAOEAE1jtVDg8e4GbhCk";
        DefaultClaims claims;
        try {
            claims = (DefaultClaims) Jwts.parser().setSigningKey(key).parse(token).getBody();
        } catch (Exception e) {
            throw new AuthenticationServiceException("Token corrupted");
        }
        if (claims.get("expirationDate", Long.class) == null)
            throw new AuthenticationServiceException("Invalid token");
        Date expiredDate = new Date(claims.get("expirationDate", Long.class));
        if (expiredDate.after(new Date())) {
            return buildFullUserAuthentication(authentication, claims);
        } else {
            throw new AuthenticationServiceException("Token expired date error");
        }
    }

    private UserAuthentication buildFullUserAuthentication(UserAuthentication authentication, DefaultClaims claims) {
        User user = (User) userService.loadUserByUsername(claims.get("login", String.class));
        if (user.isEnabled()) {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            return new UserAuthentication(authentication.getToken(), authorities, true, user);
        } else {
            throw new AuthenticationServiceException("User disabled");
        }
    }
}
