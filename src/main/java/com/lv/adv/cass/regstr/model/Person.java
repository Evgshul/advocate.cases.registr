package com.lv.adv.cass.regstr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Data
@Entity
@Table(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "personal_code", unique = true)
    @Size(min = 5, max = 12)
    private String identifier;

    @Column(nullable = false)
    @Size(min = 5, max = 50)
    private String fullName;

    @Column(nullable = false)
    private String address;

    private String phone;

    private String email;
}
