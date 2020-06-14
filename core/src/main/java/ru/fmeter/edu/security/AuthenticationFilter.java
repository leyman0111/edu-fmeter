package ru.fmeter.edu.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public AuthenticationFilter() {
        super("/test/**");
        setAuthenticationSuccessHandler((request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getRequestDispatcher(request.getServletPath()
                    + request.getPathInfo()).forward(request, response);
        });
        setAuthenticationFailureHandler((request, response, exception) -> {
            response.getOutputStream().print(exception.getMessage());
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader("token");
        if (token == null) token = request.getParameter("token");
        if (token == null) {
            UserAuthentication authentication = new UserAuthentication(null, null);
            authentication.setAuthenticated(false);
            return authentication;
        }
        UserAuthentication authentication = new UserAuthentication(token, request);
        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }
}
