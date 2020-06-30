package ru.fmeter.dao.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cmn_certificate")
@Data
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String certString;
}
