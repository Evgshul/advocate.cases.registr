package com.lv.adv.cass.regstr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "agreements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agreement {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private String format;

    @Column(name = "is_state_provided", nullable = false)
    private boolean isStateProvided;
}
