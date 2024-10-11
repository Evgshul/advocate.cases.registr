package com.lv.adv.cass.regstr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private UUID customerId;

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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}

