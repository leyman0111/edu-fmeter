package ru.fmeter.edu.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import ru.fmeter.edu.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenLogoutHandler implements LogoutHandler {
    private final TokenService tokenService;

    public TokenLogoutHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (!tokenService.reject(request.getHeader("AUTH-TOKEN"))) {
            System.out.println("Can`t logout!");
        }
    }
}
