package com.lv.adv.cass.regstr.model;

import lombok.Getter;
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

@Entity
@Table(name = "customers")
@Getter
@Setter
@ToString
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Persons person;

    @Column(name = "identifier")
    @NotNull(message = "identifier is required")
    private String identifier;

    @Column(name = "customer_name")
    @NotNull(message = "name is required")
    private String customerName;

    @Column(name = "declared_address")
    @NotNull(message = "Declared address is required")
    private String declaredAddress;

    @Column(name = "actual_address")
    private String actualAddress;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;
}

