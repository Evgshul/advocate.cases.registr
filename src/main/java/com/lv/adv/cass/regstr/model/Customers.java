package com.lv.adv.cass.regstr.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "customers")

@NoArgsConstructor
@ToString
public class Customers {

    @Id
    @GeneratedValue
    @Getter
    @Column(name = "customer_id")
    private UUID customerId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @Getter
    @Setter
    private Persons person;

    @Column(name = "identifier")
    @NotNull(message = "identifier is required")
    @Getter
    @Setter
    private String identifier;

    @Column(name = "customer_name")
    @NotNull(message = "name is required")
    @Getter
    @Setter
    private String customerName;

    @Column(name = "declared_address")
    @NotNull(message = "Declared address is required")
    @Getter
    @Setter
    private String declaredAddress;

    @Column(name = "actual_address")
    @Getter
    @Setter
    private String actualAddress;

    @Column(name = "phone")
    @Getter
    @Setter
    private String phone;

    @Column(name = "email")
    @Getter
    @Setter
    private String email;

    public Customers(Persons person,
                     String identifier,
                     String customerName,
                     String declaredAddress,
                     String actualAddress,
                     String phone,
                     String email) {
        this.person = person;
        this.identifier = identifier;
        this.customerName = customerName;
        this.declaredAddress = declaredAddress;
        this.actualAddress = actualAddress;
        this.phone = phone;
        this.email = email;
    }
}

