package ru.fmeter.dao.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cmn_user")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String pass;
    private String email;
    private String firstName;
    private String lastName;
    private String midName;
    private Date birthday;
    private String position;
    private String country;

    /**
    ISO 639-1:2002:
    * en - английский,
    * es - испанский,
    * ru - русский
    */
    private String local;
    private int rating;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Certificate> certificates;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Organization organization;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    private boolean blocked = true;
    private boolean active = false;

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public String getPassword() {
        return getPass();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
