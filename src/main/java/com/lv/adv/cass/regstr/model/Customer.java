package com.lv.adv.cass.regstr.model;

import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue
    private UUID customerId;

    @Column(nullable = false)
    private boolean isCompany;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "registration_number", unique = true)
    private String registrationNumber;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column
    @NotNull(message = "Declared address is required")
    private String declaredAddress;

    @Column(name = "actual_address")
    private String actualAddress;

    private String phone;

    private String email;

    @OneToOne
    @JoinColumn(name = "representative_id")
    private Person representative;
}

