package ru.fmeter.edu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import ru.fmeter.edu.security.TokenAuthenticationManager;
import ru.fmeter.edu.security.TokenLogoutHandler;

@Configuration
@EnableWebSecurity
public class AppConf extends WebSecurityConfigurerAdapter {
    private AuthenticationManager authenticationManager;
    private LogoutHandler logoutHandler;

    @Autowired
    private void setAuthenticationManager(TokenAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private void setLogoutHandler(TokenLogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RequestHeaderAuthenticationFilter authenticationFilter() {
        RequestHeaderAuthenticationFilter authenticationFilter = new RequestHeaderAuthenticationFilter();
        authenticationFilter.setPrincipalRequestHeader("AUTH-TOKEN");
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setExceptionIfHeaderMissing(false);
        return authenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilter(authenticationFilter())
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/activation/**").not().fullyAuthenticated()
                .antMatchers("/recovery/**").not().fullyAuthenticated()
                .antMatchers("/login").not().fullyAuthenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .disable()
                .logout()
                .permitAll()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler);
    }
}
