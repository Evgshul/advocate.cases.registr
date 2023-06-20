package com.lv.adv.cass.regstr.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customer")

@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    @Getter
    @Column(name = "customer_id")
    private UUID customerId;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    @Getter
    @Setter
    private Person person;


    public Customer(String identifier,
                    String customerName,
                    String declaredAddress,
                    String actualAddress,
                    String phone,
                    String email,
                    Person person) {

        this.identifier = identifier;
        this.customerName = customerName;
        this.declaredAddress = declaredAddress;
        this.actualAddress = actualAddress;
        this.phone = phone;
        this.email = email;
        this.person = person;
    }
}

