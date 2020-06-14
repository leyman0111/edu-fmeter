package ru.fmeter.edu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.edu.security.AuthenticationFilter;
import ru.fmeter.edu.security.UserAuthenticationManager;

@Configuration
@EnableWebSecurity
public class AppConf extends WebSecurityConfigurerAdapter {
    private UserAuthenticationManager authenticationManager;

    @Autowired
    private void setAuthenticationManager(UserAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login/registration").not().fullyAuthenticated()
                .antMatchers("/login/activation").not().fullyAuthenticated()
                .antMatchers("/login/recovery").not().fullyAuthenticated()
                .antMatchers("/login/authorization").not().fullyAuthenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .disable()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login/logout");
    }

    @Bean(name = "authenticationFilter")
    public AuthenticationFilter authenticationFilter() {
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
}
