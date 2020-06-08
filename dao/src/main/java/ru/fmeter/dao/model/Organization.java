package ru.fmeter.dao.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cmn_organization")
@Data
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String city;
    private String address;
    private String unp;
    private String okpo;

    public Organization() { }

    public Organization(@NonNull String name, @NonNull String city) {
        this.name = name;
        this.city = city;
    }
}
