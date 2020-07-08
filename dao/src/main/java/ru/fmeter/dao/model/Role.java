package ru.fmeter.dao.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "cmn_role")
@Data
public class Role implements GrantedAuthority {
    @Transient
    public final static Role SUPER_ADMIN = new Role(0L, "ROLE_SUPER_ADMIN");
    @Transient
    public final static Role ADMIN = new Role(1L, "ROLE_ADMIN");
    @Transient
    public final static Role USER = new Role(2L, "ROLE_USER");

    @Id
    private Long id;

    private String name;

    public Role() { }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
