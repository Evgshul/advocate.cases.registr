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
@Table(name = "person")
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "person_id")
    @Getter@Setter
    @GeneratedValue
    private UUID id;

    @Getter@Setter
    @NotEmpty(message = "User's identifier cannot be empty.")
    @Size(min = 5, max = 12)
    private String identifier;

    @Column(name = "fullname")
    @Getter@Setter
    @NotEmpty(message = "User's name cannot be empty.")
    @Size(min = 5, max = 50)
    private String fullName;

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

    public Person(String identifier, String fullName, String address, String phone, String email) {
        this.identifier = identifier;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}
