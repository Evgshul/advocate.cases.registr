package com.lv.adv.cass.regstr.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@ToString
@Entity
@Table(name = "persons")
@NoArgsConstructor
public class Persons {

    @Id
    @Column(name = "person_id")
    @Getter
    @GeneratedValue
    private UUID id;

    @Column(name = "fullname")
    @Getter@Setter
    @NotEmpty(message = "User's name cannot be empty.")
    @Size(min = 5, max = 50)
    private String fullName;

    @Getter@Setter
    @NotEmpty(message = "User's identifier cannot be empty.")
    @Size(min = 5, max = 10)
    private String identifier;

    @NotNull
    @Getter@Setter
    @NotEmpty(message = "User's address should not be empty.")
    private String address;

    @Getter@Setter
    @NotEmpty(message = "Phone field should not be empty.")
    private String phone;

    @Getter@Setter
    @NotEmpty(message = "Email field should not be empty.")
    @Email(message = "wrong email")
    private String email;

    public Persons(String fullName,
                   String identifier,
                   String address,
                   String phone,
                   String email) {
        this.fullName = fullName;
        this.identifier = identifier;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}
