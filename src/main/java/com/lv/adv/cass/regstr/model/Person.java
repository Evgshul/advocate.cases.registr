package com.lv.adv.cass.regstr.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@ToString
@Entity
@Table(name = "person")
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "person_id")
    @Getter
    @Setter
    @GeneratedValue
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Size(min = 5, max = 12)
    private String identifier;

    @Column(name = "fullname")
    @Getter
    @Setter
    @NotNull
    @Size(min = 5, max = 50)
    private String fullName;

    @NotNull
    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    @NotNull
    private String phone;

    @Getter
    @Setter
    @NotNull
    private String email;

    public Person(String identifier, String fullName, String address, String phone, String email) {
        this.identifier = identifier;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}
