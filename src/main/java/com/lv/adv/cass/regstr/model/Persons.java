package com.lv.adv.cass.regstr.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "fullname")
    @Getter@Setter
    private String fullName;

    @NotNull
    @Getter@Setter
    private String identifier;

    @NotNull
    @Getter@Setter
    private String address;

    @Getter@Setter
    private String phone;

    @Getter@Setter
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
